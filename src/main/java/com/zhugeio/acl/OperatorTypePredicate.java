package com.zhugeio.acl;

@FunctionalInterface
public interface OperatorTypePredicate {

  /**
   * 判断一个用户是否属于某个操作者类型
   * @param aclUser  目标用户
   * @param typeIdentifier 操作者类型描述
   * @return
   */
  boolean isBeloneTo(ACLUser aclUser, ACLModel aclModel, String typeIdentifier);
}
