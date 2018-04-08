package com.zhuge.draft;

import com.zhugeio.acl.ACLModel;
import com.zhugeio.acl.ACLResources;
import java.util.Objects;

public class Draft implements ACLModel {

  // 草稿状态常量
  public interface Status {

    String SAVED = "saved"; // 已经保存，但是未提交

    String CHECKING = "checking"; // 审核中

    String CHECK_FAILURE = "check_failure";  // 审核失败

    String SUBMITTED = "submitted"; // 草稿已经正式提交
  }

  public interface Operations {

    String VIEW = "view"; // 查看

    String EDIT = "edit"; // 编辑

    String SAVE = "save"; // 保存

    String SUBMIT_TO_CHECK = "submit_to_check"; // 提交审核

    String CANCEL_CHECK_SUBMIT = "cancel_check_submit"; // 取消审核

    String CHECK_PASS = "check_pass"; // 审核通过

    String CHECK_FAILURE = "check_failure"; // 审核失败

    String DELETE = "delete";
  }

  private final long id;

  private final String title;

  private final String content;

  private final int author;

  private final String status;

  public Draft(long id, String title, String content, int author, String status) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.author = author;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public int getAuthor() {
    return author;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String getACLResource() {
    return ACLResources.DRAFT;
  }

  @Override
  public String getACLStatus() {
    return getStatus();
  }

  @Override
  public int getOwnerId() {
    return this.getAuthor();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Draft draft = (Draft) o;
    return id == draft.id &&
        author == draft.author &&
        Objects.equals(title, draft.title) &&
        Objects.equals(content, draft.content) &&
        Objects.equals(status, draft.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content, author, status);
  }

  @Override
  public String toString() {
    return "Draft{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", author=" + author +
        ", status='" + status + '\'' +
        '}';
  }
}
