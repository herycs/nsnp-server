package com.herycs.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Group
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/5 0:39
 * @Version V1.0
 **/
@Entity
@Table(name="tb_usergroup")
public class Group {

    @Id
    private String id;
    private String columnid;
    private String uid;
    private int type;

    public Group() {
    }

    public Group(String id, String columnid, String uid, int type) {
        this.id = id;
        this.columnid = columnid;
        this.uid = uid;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
