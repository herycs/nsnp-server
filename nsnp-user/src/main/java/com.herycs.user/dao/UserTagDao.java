package com.herycs.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.herycs.user.pojo.UserTag;

import java.util.List;

/**
 * @ClassName UserTagDao
 * @Description [用户标签查询]
 * @Author ANGLE0
 * @Date 2021/4/28 0:25
 * @Version V1.0
 **/
public interface UserTagDao extends JpaRepository<UserTag,String>, JpaSpecificationExecutor<UserTag> {

    @Modifying
    @Query(value = "select * from tb_tag where uid=? and state>0 order by score desc", nativeQuery = true)
    List<UserTag> findByUid(String uid);

    UserTag findByColumnidAndUid(String tag, String uid);
}
