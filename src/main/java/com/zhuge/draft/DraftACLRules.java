package com.zhuge.draft;

import com.zhuge.draft.Draft.Operations;
import com.zhuge.draft.Draft.Status;
import com.zhugeio.acl.ACLLogic;
import com.zhugeio.acl.ACLOperatorType;
import com.zhugeio.acl.ACLResources;
import com.zhugeio.acl.Roles;

public class DraftACLRules {

  private DraftACLRules() {

  }

  public static void declareRules(ACLLogic aclLogic) {
    // 设置草稿权限树

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

    /* 当草稿处于审核中状态 **/
    // 管理员角色均可以查看
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECKING, Operations.VIEW, ACLOperatorType.ROLE, Roles.ADMIN);
    // 管理员角色均可以编辑
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECKING, Operations.EDIT, ACLOperatorType.ROLE, Roles.ADMIN);
    // 管理员角色可以通过审核
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECKING, Operations.CHECK_PASS, ACLOperatorType.ROLE, Roles.ADMIN);
    // 管理员角色可以设置审核失败
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECKING, Operations.CHECK_FAILURE, ACLOperatorType.ROLE, Roles.ADMIN);
    // 编辑者可以查看自己的草稿
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECKING, Operations.VIEW, ACLOperatorType.OWNER, "");
    // 编辑者可以撤回自己提交的草稿
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECKING, Operations.CANCEL_CHECK_SUBMIT, ACLOperatorType.OWNER, "");

    /* 当草稿处于审核失败状态 **/
    // 管理员角色均可以查看
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECK_FAILURE, Operations.VIEW, ACLOperatorType.ROLE, Roles.ADMIN);
    // 编辑者可以查看自己的
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECK_FAILURE, Operations.VIEW, ACLOperatorType.OWNER, "");
    // 编辑者可以重新编辑
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECK_FAILURE, Operations.EDIT, ACLOperatorType.OWNER, "");
    // 编辑者可以删除
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECK_FAILURE, Operations.DELETE, ACLOperatorType.OWNER, "");
    // 编辑者可以保存
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.CHECK_FAILURE, Operations.SAVE, ACLOperatorType.OWNER, "");
    // 编辑者可以再次提交审核
    aclLogic.addACLRule(
        ACLResources.DRAFT, Status.SAVED, Operations.SUBMIT_TO_CHECK, ACLOperatorType.OWNER, "");
  }
}
