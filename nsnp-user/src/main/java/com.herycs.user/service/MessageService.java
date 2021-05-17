package com.herycs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.user.dao.MessageDao;
import com.herycs.user.pojo.Message;

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

    public List<Message> findAll() {
        return messageDao.findAll();
    }

    public void addRecord(Message messageDto) {
        messageDto.setTime(currTime());

        messageDao.save(messageDto);
    }

    public List<Message> getRecord(String uid, String receptorId) {
        return messageDao.findByUserAndReceptor(uid, receptorId, 20);
    }

    public List<String> getAllSender(String uid) {
        return messageDao.findAllSender(uid);
    }

    public List<String> getAllReceptor(String uid, String type) {
        return messageDao.findAllReceptor(uid, type);
    }

    public Message getLastMessage(String uid, String receptor, String type) {
        return messageDao.findLastMessage(uid, receptor, type);
    }

    public List<Message> getAllSystemNotice(String uid, String type) {
        return messageDao.findAllSystemNotice(uid, type);
    }

    public Message getSystemNotice(String uid, String type) {
        return messageDao.findLastNotice(uid, type);
    }

    private String currTime() {
        return format.format(System.currentTimeMillis());
    }

}
