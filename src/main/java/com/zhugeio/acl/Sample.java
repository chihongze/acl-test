package com.zhugeio.acl;

import com.zhuge.draft.Draft;
import com.zhuge.draft.Draft.Status;
import com.zhuge.draft.DraftACLRules;
import com.zhuge.draft.DraftService;
import com.zhuge.draft.DraftServiceImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Sample {

  public static void main(String[] args) throws Exception {
    final ACLLogic aclLogic = new SimpleMemoryACLLogic();

    // 初始化角色
    final Map<String, Set<Integer>> roles = new HashMap<>();
    // 管理员
    roles.put(Roles.ADMIN, new HashSet<Integer>() {
      {
        add(1);
        add(2);
        add(3);
      }
    });
    // 运营人员
    roles.put(Roles.OPS, new HashSet<Integer>() {
      {
        add(4);
        add(5);
      }
    });

    // 注册角色判断逻辑
    aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.ALL, (u, m, p) -> true);
    aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.OWNER, (u, m, p) -> u.getUserId() == m.getOwnerId());
    aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.ROLE, (u, m, p) -> roles.get(p).contains(u.getUserId()));
    aclLogic.registerOperatorTypePredicator(
        ACLOperatorType.USER, (u, m, p) -> u.getUserId() == Integer.parseInt(p));

    // 声明草稿权限规则
    DraftACLRules.declareRules(aclLogic);

    final DraftService draftService = new DraftServiceImpl(aclLogic);
    Draft savedDraft = new Draft(0L, "test", "test draft", 4, Status.SAVED);
    // 作者自身可以提交审核
    draftService.submitToCheck(savedDraft, new User(4, "Sam"));
    // 它人提交审核就不行了
    try {
      draftService.submitToCheck(savedDraft, new User(1, "Jack"));
    } catch (IllegalAccessException e) {
      System.out.println(e);
    }
  }
}
