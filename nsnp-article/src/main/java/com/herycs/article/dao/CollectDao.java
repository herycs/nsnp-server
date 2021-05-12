package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.herycs.article.pojo.Channel;
import com.herycs.article.pojo.Collect;

import java.util.List;

/**
 * @ClassName CollectDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/3 23:18
 * @Version V1.0
 **/
public interface CollectDao extends JpaRepository<Collect,String>, JpaSpecificationExecutor<Collect> {

    List<Collect> findByUidOrderByTimeDesc(String uid);

}
