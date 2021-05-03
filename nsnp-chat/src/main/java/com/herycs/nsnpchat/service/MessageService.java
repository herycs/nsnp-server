package com.herycs.nsnpchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.nsnpchat.dao.MessageDao;
import com.herycs.nsnpchat.model.MessageDto;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName MessageService
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/26 22:04
 * @Version V1.0
 **/
@Service
public class MessageService {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MessageDao messageDao;

    public List<MessageDto> findAll() {
        return messageDao.findAll();
    }

    public void addRecord(MessageDto messageDto) {
        messageDto.setTime(currTime());

        messageDao.save(messageDto);
    }

    public List<MessageDto> getRecord(String uid, String receptorId) {
        return messageDao.findByUserAndReceptor(uid, receptorId, 20);
    }

    private String currTime() {
        return format.format(System.currentTimeMillis());
    }

}
