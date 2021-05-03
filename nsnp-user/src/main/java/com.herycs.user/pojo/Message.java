package com.herycs.user.pojo;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * @ClassName MessageDto
 * @Description [消息实体]
 * @Author ANGLE0
 * @Date 2021/4/24 17:40
 * @Version V1.0
 **/

@Entity
@Table(name = "chat_record")
public class Message implements Serializable {

    @Id
    private Integer id;
    @Nullable
    private String sender;
    @Nullable
    private String receptor;
    private String type;
    private String msg;
    @Nullable
    private String time;

    public Message(String messageType, String msg) {
        this.type = messageType;
        this.msg = msg;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public String getSender() {
        return sender;
    }

    public void setSender(@Nullable String sender) {
        this.sender = sender;
    }

    @Nullable
    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(@Nullable String receptor) {
        this.receptor = receptor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Nullable
    public String getTime() {
        return time;
    }

    public void setTime(@Nullable String time) {
        this.time = time;
    }
}
