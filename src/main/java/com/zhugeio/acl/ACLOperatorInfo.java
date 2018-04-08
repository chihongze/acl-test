package com.zhugeio.acl;

import java.util.Objects;

public class ACLOperatorInfo {

  private final ACLOperatorType operatorType;

  private final String operatorIdentifier;

  public ACLOperatorInfo(ACLOperatorType operatorType, String operatorIdentifier) {
    this.operatorType = operatorType;
    this.operatorIdentifier = operatorIdentifier;
  }

  public ACLOperatorType getOperatorType() {
    return operatorType;
  }

  public String getOperatorIdentifier() {
    return operatorIdentifier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ACLOperatorInfo that = (ACLOperatorInfo) o;
    return operatorType == that.operatorType &&
        Objects.equals(operatorIdentifier, that.operatorIdentifier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operatorType, operatorIdentifier);
  }

  @Override
  public String toString() {
    return "ACLOperatorInfo{" +
        "operatorType=" + operatorType +
        ", operatorIdentifier='" + operatorIdentifier + '\'' +
        '}';
  }
}
