package com.herycs.user.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Firend
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 0:50
 * @Version V1.0
 **/
@Entity
@Table(name="tb_friend")
public class Friend {

    @Id
    private int id;
    private String uid;
    private String friendid;
    private int islike;
    private long time;

    public Friend() {
    }

    public Friend(int id, String uid, String friendid, int islike, long time) {
        this.id = id;
        this.uid = uid;
        this.friendid = friendid;
        this.islike = islike;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

