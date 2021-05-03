package com.herycs.friend.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.herycs.friend.friend.pojo.Friend;

public interface FriendDao extends JpaRepository<Friend, String> {
    public Friend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=? WHERE userid = ? AND friendid = ?", nativeQuery = true)
    public void updateIslike(String islike, String userid, String friendid);

    @Modifying
    @Query(value = "delete FROM tb_friend WHERE userid = ? AND friendid = ?", nativeQuery = true)
    void deletefriend(String userid, String friendid);
}
