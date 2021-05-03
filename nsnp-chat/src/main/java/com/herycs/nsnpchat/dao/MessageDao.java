package com.herycs.nsnpchat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.herycs.nsnpchat.model.MessageDto;

import java.util.List;

/**
 * @ClassName MessageDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/26 22:04
 * @Version V1.0
 **/
public interface MessageDao extends JpaRepository<MessageDto, String> {

    @Query(value = "select * from tb_record where sender=? or sender=? order by time desc limit ?", nativeQuery = true)
    public List<MessageDto> findByUserAndReceptor(String uid, String receptorId, int limit);
}
