package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value = "select * from itaobaob_article.tb_collect where uid = ? and state = 1 group by aid order by time desc", nativeQuery = true)
    List<Collect> findByUid(String uid);

    @Query(value = "select * from itaobaob_article.tb_collect where uid=? and aid=?", nativeQuery = true)
    Collect getCollect(String uid, String aid);

}
