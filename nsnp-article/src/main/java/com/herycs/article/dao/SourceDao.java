package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.herycs.article.pojo.Source;

import java.util.List;


/**
 * @ClassName SourceDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 14:55
 * @Version V1.0
 **/
public interface SourceDao extends JpaRepository<Source,String>, JpaSpecificationExecutor<Source> {

    List<Source> findByUidAndState(String uid, int state);

    @Query(value = "select * from tb_source where name like %?%", nativeQuery = true)
    List<Source> findByName(String name);

    @Query(value = "select * from tb_source where id = ?", nativeQuery = true)
    Source findById(int id);
}
