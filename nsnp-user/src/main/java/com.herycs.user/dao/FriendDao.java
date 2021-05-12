package com.herycs.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.herycs.user.pojo.Friend;

import java.util.List;

/**
 * @ClassName FriendDao
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 0:52
 * @Version V1.0
 **/
public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {


    List<Friend> findByUidAndIslike(String uid, int isLike);

    List<Friend> findByFriendidAndIslike(String friend, int isLike);

    @Query(value = "select count(*) from tb_friend where friendid=? and isLike=?", nativeQuery = true)
    int getFunCount(String uid, int isLike);

    @Query(value = "select count(*) from tb_friend where uid=? and isLike=?", nativeQuery = true)
    int getLikeCount(String uid, int isLike);



}
