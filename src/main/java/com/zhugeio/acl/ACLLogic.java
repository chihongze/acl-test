package com.zhugeio.acl;

public interface ACLLogic {

  /**
   * 添加权限控制规则
   * @param resource  资源类型
   * @param status  资源状态
   * @param operation  该状态下的操作
   * @param operatorType  操作者类型
   * @param operatorIdentifier  操作者标识
   * @return  添加结果
   */
  boolean addACLRule(
      String resource, String status, String operation,
      ACLOperatorType operatorType, String operatorIdentifier);

  /**
   * 判断aclUser对aclModel的operation操作是否拥有权限
   * @param aclModel 权限控制目标对象
   * @param operation 针对目标对象的操作
   * @param aclUser  访问用户
   * @return 如果允许访问，返回true，否则返回false
   */
  boolean isAllowAccess(
      ACLModel aclModel, String operation, ACLUser aclUser);

  /**
   * 注册操作者类型判断器
   * @param operatorType 目标操作者类型
   * @param predicate 判断逻辑
   */
  void registerOperatorTypePredicator(
      ACLOperatorType operatorType, OperatorTypePredicate predicate);
}
