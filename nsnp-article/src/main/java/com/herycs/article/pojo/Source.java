package com.herycs.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Source
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 14:46
 * @Version V1.0
 **/
@Entity
@Table(name="tb_source")
public class Source {
    @Id
    private Integer id;
    private String name;
    private String path;
    private String uid;
    private String tag;
    private long uploadtime;
    private long updatetime;
    private int state;

    public Source() {
    }

    public Source(Integer id, String name, String path, String uid, String tag, long uploadtime, long updatetime, int state) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.uid = uid;
        this.tag = tag;
        this.uploadtime = uploadtime;
        this.updatetime = updatetime;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(long uploadtime) {
        this.uploadtime = uploadtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
