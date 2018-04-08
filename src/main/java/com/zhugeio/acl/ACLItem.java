package com.zhugeio.acl;

import java.util.Objects;

public class ACLItem {

  private final String resource;

  private final String status;

  private final String operation;

  public ACLItem(String resource, String status, String operator) {
    this.resource = resource;
    this.status = status;
    this.operation = operator;
  }

  public String getResource() {
    return resource;
  }

  public String getStatus() {
    return status;
  }

  public String getOperation() {
    return operation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ACLItem aclItem = (ACLItem) o;
    return Objects.equals(resource, aclItem.resource) &&
        Objects.equals(status, aclItem.status) &&
        Objects.equals(operation, aclItem.operation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resource, status, operation);
  }

  @Override
  public String toString() {
    return "ACLItem{" +
        "resource='" + resource + '\'' +
        ", status='" + status + '\'' +
        ", operation='" + operation + '\'' +
        '}';
  }
}
