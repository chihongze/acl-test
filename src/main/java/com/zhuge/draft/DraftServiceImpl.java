package com.zhuge.draft;

import com.zhuge.draft.Draft.Operations;
import com.zhugeio.acl.ACLLogic;
import com.zhugeio.acl.User;

public class DraftServiceImpl implements DraftService {

  private final ACLLogic aclLogic;

  public DraftServiceImpl(ACLLogic aclLogic) {
    this.aclLogic = aclLogic;
  }

  @Override
  public void save(Draft draft, User operator) throws IllegalAccessException {
    if (!aclLogic.isAllowAccess(draft, Operations.SAVE, operator)) {
      throw new IllegalAccessException("not allow save!");
    }
  }

  @Override
  public void delete(Draft draft, User operator) throws IllegalAccessException {
    if (!aclLogic.isAllowAccess(draft, Operations.DELETE, operator)) {
      throw new IllegalAccessException("not allow delete!");
    }
  }

  @Override
  public void submitToCheck(Draft draft, User operator) throws IllegalAccessException{
    if (!aclLogic.isAllowAccess(draft, Operations.SUBMIT_TO_CHECK, operator)) {
      throw new IllegalAccessException("not allow submit to check");
    }
  }

  @Override
  public void checkPass(Draft draft, User operator) throws IllegalAccessException {
    if (!aclLogic.isAllowAccess(draft, Operations.CHECK_PASS, operator)) {
      throw new IllegalAccessException("not allow check pass");
    }
  }

  @Override
  public void checkFailure(Draft draft, User operator) throws IllegalAccessException {
    if (!aclLogic.isAllowAccess(draft, Operations.CHECK_FAILURE, operator)) {
      throw new IllegalAccessException("not alow check failure");
    }
  }
}
