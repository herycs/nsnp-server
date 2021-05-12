package com.herycs.user.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * @ClassName Tag
 * @Description [用户标签]
 * @Author ANGLE0
 * @Date 2021/4/28 0:13
 * @Version V1.0
 **/
@Entity
@Table(name="tb_tag")
public class UserTag implements Serializable {

    @Id
    private String id;
    private String uid;
    private String columnid;
    private float score;
    private String state;

    public UserTag() {
    }

    public UserTag(String id, String uid, String columnid, float score, String state) {
        this.id = id;
        this.uid = uid;
        this.columnid = columnid;
        this.score = score;
        this.state = state;
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

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
