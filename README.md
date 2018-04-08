# Introduce

## 概述

一个基于简单匹配树的权限控制系统，树的层次结构如下：

Resource -> Status -> Operation -> OperatorType -> OperatorIdentifier

```
Resource(资源类型)
  Status1(资源状态1)
    operation1(状态1下允许的操作1)
      operator_type1(操作者类型) operator_id1(具体的操作者标识)
      operator_type1(操作者类型) operator_id2(具体的操作者标识)
      operator_type2 *
        ...
    operation2(状态1下允许的操作2)
      operator_type1(操作者类型)
        ...
  Status2(资源状态2)
    operation1(状态1下允许的操作1)
    operation2(状态1下允许的操作2)
  ...
```

* Resource: 资源类型，通常是权限操作的核心Domain对象，比如文章、商品、看板、活动等"物品"
* Status: 资源状态，物品通常在不同的生命周期中有不同的状态，比如商品，有上架、下架、缺货等等，不同的状态下允许的操作不同。
* Operation: 针对某个状态下资源的操作，在代码中通常是服务接口，比如文章在保存状态下可以拥有提交审核、删除、查看、编辑等操作。
* OperatorType: 权限控制的操作者类型，内置了以下类型：ALL - 所有人，OWNER - 资源拥有者自身，ROLE - 按照用户角色、USER - 按照具体用户，也可以根据业务需要自定义操作者类型
* OperatorIdentifier: 操作者标识，随OperatorType不同而不同，当OperatorType是ALL的时候和OWNER的时候无需identifier，当OperatorType为ROLE的时候，identifier为角色名，当OperatorType为USER的时候，identifier为具体用户ID

## 使用流程

**S1. 设置Operator匹配逻辑**

权限问题的核心在于回答“用户是否属于指定的用户群”这个问题，但用户群的概念并没有一个统一标准，它可能是静态而固定的，也有可能是一段动态的逻辑。

我们通过OperatorType抽象出了用户群类型，通过向ACLLogic注册一段回调逻辑来判断用户是否属于该用户群：

```java
final ACLLogic aclLogic = new SimpleMemoryACLLogic();

aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.ALL, (u, m, p) -> true);

aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.OWNER, (u, m, p) -> u.getUserId() == m.getOwnerId());

aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.ROLE, (u, m, p) -> roles.get(p).contains(u.getUserId()));

aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.USER, (u, m, p) -> u.getUserId() == Integer.parseInt(p));
```

Lambda表达式基于OperatorTypePredicate接口：

```java
package com.zhugeio.acl;

@FunctionalInterface
public interface OperatorTypePredicate {

  /**
   * 判断一个用户是否属于某个操作者类型
   * @param aclUser  目标用户
   * @param operatorIdentifier 操作者类型描述
   * @return
   */
  boolean isBeloneTo(ACLUser aclUser, ACLModel aclModel, String operatorIdentifier);
}

```

可以结合用户、操作对象以及权限声明中的operatorIdentifier来动态的判断权限。

**S2. 声明业务权限规则**

这块可以维护在数据库或者配置文件中，也可以像下面这样使用代码声明：

```java
/* 当草稿处于保存未提交状态 **/
    // 编辑者可以查看自己的草稿
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.VIEW, ACLOperatorType.OWNER, "");
    // 编辑者可以重新编辑自己的草稿
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.EDIT, ACLOperatorType.OWNER, "");
    // 编辑者可以重新保存
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.SAVE, ACLOperatorType.OWNER, "");
    // 编辑者可以删除自己的草稿
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.DELETE, ACLOperatorType.OWNER, "");
    // 编辑者可以提交审核
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.SUBMIT_TO_CHECK, ACLOperatorType.OWNER, "");
    // 管理员可有直接审核通过，正式提交
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.CHECK_PASS, ACLOperatorType.OWNER, "");
```



**S3. 在业务逻辑中检查权限**

通常在Service接口中对权限进行检查，如果不使用框架，我们需要写下复杂的逻辑判断来结合对象状态和用户身份进行检查，但现在只需要调一个方法即可：

```java
@Override
  public void submitToCheck(Draft draft, User operator) throws IllegalAccessException{
    if (!aclLogic.isAllowAccess(draft, Operations.SUBMIT_TO_CHECK, operator)) {
      throw new IllegalAccessException("not allow submit to check");
    }
  }
```

可以结合AOP来做到更加方便的判断。