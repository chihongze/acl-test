package com.zhugeio.acl;

public enum ACLOperatorType {

  // 所有人
  ALL(0, "all"),

  // 资源拥有者自身
  OWNER(1, "owner"),

  // 具体用户
  USER(1, "user"),

  // 角色
  ROLE(2, "role")
  ;

  private final int code;

  private final String type;

  ACLOperatorType(int code, String type) {
    this.code = code;
    this.type = type;
  }

  public int getCode() {
    return this.code;
  }

  public String getType() {
    return this.type;
  }
}
