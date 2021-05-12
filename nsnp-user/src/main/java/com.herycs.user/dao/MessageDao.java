package com.herycs.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.herycs.user.pojo.Message;

import java.util.List;

/**
 * @ClassName MessageDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/26 22:04
 * @Version V1.0
 **/
public interface MessageDao extends JpaRepository<Message, String> {

    @Query(value = "select * from tb_record where sender=? or sender=? order by time desc limit ?", nativeQuery = true)
    public List<Message> findByUserAndReceptor(String uid, String receptorId, int limit);

    @Query(value = "select receptor from tb_record where sender=? group by receptor", nativeQuery = true)
    List<String> findAllReceptor(String uid);

    @Query(value = "select receptor from tb_record where receptor=? group by sender", nativeQuery = true)
    List<String> findAllSender(String uid);

    @Query(value = "select * from tb_record where sender=? and receptor=? and type=? order by time desc limit 1", nativeQuery = true)
    Message findLastMessage(String uid, String receptor, String type);

    @Query(value = "select * from tb_record where receptor=? and type=? order by time desc limit 1", nativeQuery = true)
    Message findLastNotice(String receptor, String type);

    @Query(value = "select * from tb_record where receptor=? and type=?", nativeQuery = true)
    List<Message> findAllSystemNotice(String receptor, String type);

}
