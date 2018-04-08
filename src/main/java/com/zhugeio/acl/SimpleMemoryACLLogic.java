package com.zhugeio.acl;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleMemoryACLLogic implements ACLLogic {

  private final Map<ACLItem, Set<ACLOperatorInfo>> rules = new HashMap<>();

  private final EnumMap<ACLOperatorType, OperatorTypePredicate> operatorPredicates = new EnumMap<>(ACLOperatorType.class);

  @Override
  public boolean addACLRule(String resource, String status, String operation,
      ACLOperatorType operatorType, String operatorIdentifier) {
    ACLItem aclItem = new ACLItem(resource, status, operation);
    return rules.computeIfAbsent(aclItem, k -> new HashSet<>()).add(
        new ACLOperatorInfo(operatorType, operatorIdentifier));
  }

  @Override
  public boolean isAllowAccess(ACLModel aclModel, String operation, ACLUser aclUser) {
    ACLItem aclItem = new ACLItem(aclModel.getACLResource(), aclModel.getACLStatus(), operation);
    Set<ACLOperatorInfo> operators = rules.get(aclItem);

    // 找不到权限设置
    if (operators == null || operators.size() == 0) {
      return false;
    }

    for (ACLOperatorInfo operatorInfo : operators) {
      OperatorTypePredicate predicate = operatorPredicates.get(operatorInfo.getOperatorType());
      // 只要有一个满足了操作条件，那么就可以授权访问了
      if (predicate.isBeloneTo(aclUser, aclModel, operatorInfo.getOperatorIdentifier())) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void registerOperatorTypePredicator(ACLOperatorType operatorType,
      OperatorTypePredicate predicate) {
    operatorPredicates.put(operatorType, predicate);
  }
}
