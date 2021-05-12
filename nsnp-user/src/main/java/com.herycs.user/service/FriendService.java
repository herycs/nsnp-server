package com.herycs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.user.dao.FriendDao;
import com.herycs.user.pojo.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FriendService
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 0:53
 * @Version V1.0
 **/
@Service
public class FriendService {


    @Autowired
    private FriendDao friendDao;

    public List<Friend> findUserFriend(String uid) {
        List<Friend> likeUser = friendDao.findByFriendidAndIslike(uid, 1);

        List<Friend> userLike = friendDao.findByUidAndIslike(uid, 1);

        ArrayList<Friend> userFriendList = new ArrayList<>();

        for (int i = 0; i < userLike.size(); i++) {
            if (likeUser.contains(userLike.get(i))) {
                userFriendList.add(userLike.get(i));
            }
        }

        return userFriendList;
    }

    public List<Friend> findUserLike(String uid) {
        return friendDao.findByUidAndIslike(uid, 1);
    }

    public List<Friend> findLikeUser(String uid) {
        return friendDao.findByFriendidAndIslike(uid, 1);
    }

    public void updateFriend(String uid, String friendId, int isLike) {
        Friend friend = new Friend();

        friend.setUid(uid);
        friend.setFriendid(friendId);
        friend.setIslike(isLike);
        friend.setTime(System.currentTimeMillis());
    }

    public int getFunCount(String uid, int isLike) {
        return friendDao.getFunCount(uid, isLike);
    }

    public int getLikeCount(String uid, int isLike) {
        return friendDao.getLikeCount(uid, isLike);
    }

}
