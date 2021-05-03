package com.herycs.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * @ClassName Comment
 * @Description [文章评论]
 * @Author ANGLE0
 * @Date 2021/4/27 23:20
 * @Version V1.0
 **/
@Entity
@Table(name="tb_comment")
public class ArticleComment implements Serializable {

    @Id
    private String id;

    private String uid;
    private String parentid;
    private String articleid;
    private String ctime;
    private String content;

    public ArticleComment() {
    }

    public ArticleComment(String id, String uid, String parentid, String articleid, String ctime, String content) {
        this.id = id;
        this.uid = uid;
        this.parentid = parentid;
        this.articleid = articleid;
        this.ctime = ctime;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
