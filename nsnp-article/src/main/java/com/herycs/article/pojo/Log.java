package com.herycs.article.pojo;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Log
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/29 13:16
 * @Version V1.0
 **/
@Entity
@Table(name="tb_log")
public class Log {

    @Id
    private int id;
    private String uid;
    private String aid;
    private String columnid;
    private String channelid;
    private int type;
    private long time;

    public Log() {
    }

    public Log(String uid, String aid, String columnid, String channelid, int type, long time) {
        this.uid = uid;
        this.aid = aid;
        this.columnid = columnid;
        this.channelid = channelid;
        this.type = type;
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

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
