package com.herycs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.common.util.IdWorker;
import com.herycs.user.dao.UserTagDao;
import com.herycs.user.pojo.UserTag;

import java.util.List;

/**
 * @ClassName UserTagService
 * @Description [标签]
 * @Author ANGLE0
 * @Date 2021/4/28 0:28
 * @Version V1.0
 **/
@Service
public class UserTagService {

    @Autowired
    private UserTagDao userTagDao;

    @Autowired
    private IdWorker idWorker;

    public List<UserTag> findUserTags(String uid) {
        return userTagDao.findByUid(uid);
    }

    // 获取用户标签集合
    public UserTag findByTag(String tag, String uid) {
        return userTagDao.findByColumnidAndUid(tag, uid);
    }

    // 添加新标签
    public void addTag(String uid, String columnId) {

        UserTag userTag = new UserTag();

        userTag.setUid(uid);
        userTag.setId(idWorker.nextId() + "");
        userTag.setColumnid(columnId);
        userTag.setScore(1);
        userTag.setState("1");

        userTagDao.save(userTag);
    }

    // 更新标签比重
    public void update(String uid, String tag, int opr) {

        UserTag userTag = userTagDao.findByColumnidAndUid(tag, uid);

        if (userTag == null) {
            return;
        }

        // 查看 + 0.1分
        float newScore = getNewScore(userTag.getScore(), opr);
        if (newScore == Integer.MIN_VALUE) {
            userTag.setState("0");
        } else {
            userTag.setScore(newScore);
        }

        userTagDao.save(userTag);
    }

    private float getNewScore(float currScore, int opr) {

        switch (opr) {
            // 收藏
            case 4:
                return currScore + 0.4f;
            // 评论
            case 3:
                return currScore + 0.3f;
            // 赞
            case 2:
                return currScore + 0.2f;
            // 访问
            case 1:
                return currScore + 0.1f;
            // 踩
            case 0:
                return currScore - 0.2f;
            // 不喜欢
            case -1:
                return currScore - 0.5f;
        }
        return Integer.MIN_VALUE;
    }

}
