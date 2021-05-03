package com.herycs.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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

    List<UserTag> findByUidOrderByScoreDesc(String uid);

    UserTag findByTagAndUid(String tag, String uid);
}
