package com.herycs.friend.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herycs.friend.friend.pojo.NoFriend;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    public NoFriend findByUseridAndFriendid(String userid, String friendid);
}
