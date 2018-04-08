package com.zhugeio.acl;

/**
 * ACLModel用于描述一个需要控制操作权限的实体对象
 */
public interface ACLModel {

  /**
   * 获取该实体的控制资源类型
   */
  String getACLResource();

  /**
   * 获取该实体当前与ACL相关联的状态
   */
  String getACLStatus();

  /**
   * 获取该实体当前拥有者的ID信息
   */
  int getOwnerId();
}
