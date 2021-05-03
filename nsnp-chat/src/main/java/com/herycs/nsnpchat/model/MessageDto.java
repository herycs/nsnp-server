package com.herycs.nsnpchat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.io.Serializable;

/**
 * @ClassName MessageDto
 * @Description [消息实体]
 * @Author ANGLE0
 * @Date 2021/4/24 17:40
 * @Version V1.0
 **/

@Entity
@Table(name = "tb_record")
public class MessageDto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Nullable
    private String sender;
    @Nullable
    private String receptor;
    private String type;
    private String msg;
    @Nullable
    private String time;

    public MessageDto(String messageType, String msg) {
        this.type = messageType;
        this.msg = msg;
    }

    public MessageDto() {
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
