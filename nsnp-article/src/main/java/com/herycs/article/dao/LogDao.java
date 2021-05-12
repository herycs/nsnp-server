package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.herycs.article.pojo.Log;

import java.util.List;

/**
 * @ClassName LogDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/29 13:19
 * @Version V1.0
 **/
public interface LogDao extends JpaRepository<Log,String>, JpaSpecificationExecutor<Log> {

    public List<Log> findByUidAndTypeOrderByTimeDesc(String uid, int type);

}
