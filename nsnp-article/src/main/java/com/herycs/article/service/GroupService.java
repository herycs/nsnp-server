package com.herycs.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.article.dao.GroupDao;
import com.herycs.article.pojo.Group;

import java.util.List;

/**
 * @ClassName GroupService
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/5 0:44
 * @Version V1.0
 **/
@Service
public class GroupService {

    @Autowired
    private GroupDao groupDao;

    // 加入圈子
    public void addGroup(String uid, String gid, int type) {
        Group group = new Group();
        group.setUid(uid);
        group.setColumnid(gid);
        group.setType(type);

        groupDao.save(group);
    }

    public Group findByGroupId(int id) {
        return groupDao.findByIdent(id);
    }

    public List<Group> findGroupList(String uid, int type) {
        return groupDao.findByUidAndType(uid, type);
    }

}
