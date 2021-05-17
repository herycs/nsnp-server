package com.herycs.article.pojo;

import javax.persistence.*;

/**
 * @ClassName Collect
 * @Description [收藏]
 * @Author ANGLE0
 * @Date 2021/5/3 23:16
 * @Version V1.0
 **/
@Entity
@Table(name = "tb_collect")
public class Collect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;
    private String aid;
    private long time;
    private Integer state;

    public Collect() {
    }

    public Collect(Integer id, String uid, String aid, long time, Integer state) {
        this.id = id;
        this.uid = uid;
        this.aid = aid;
        this.time = time;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
