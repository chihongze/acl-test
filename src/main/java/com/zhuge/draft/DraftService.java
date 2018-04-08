package com.zhuge.draft;

import com.zhugeio.acl.User;

public interface DraftService {

  /**
   *  增
   */
  void save(Draft draft, User operator) throws IllegalAccessException;

  /**
   *  删
   */
  void delete(Draft draft, User operator) throws IllegalAccessException;

  /**
   * 提交审核
   */
  void submitToCheck(Draft draft, User operator) throws IllegalAccessException;

  /**
   * 审核通过
   */
  void checkPass(Draft draft, User operator) throws IllegalAccessException;

  /**
   * 审核不通过
   */
  void checkFailure(Draft draft, User operator) throws IllegalAccessException;
}
