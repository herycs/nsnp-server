package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.herycs.article.pojo.Group;
import com.herycs.article.pojo.Log;

import java.util.List;

/**
 * @ClassName GroupDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/5 0:43
 * @Version V1.0
 **/
public interface GroupDao extends JpaRepository<Group,String>, JpaSpecificationExecutor<Group> {

    List<Group> findByUidAndType(String uid, int type);

    @Query(value = "select * from tb_usergroup where id=?", nativeQuery = true)
    Group findByIdent(int id);

}
