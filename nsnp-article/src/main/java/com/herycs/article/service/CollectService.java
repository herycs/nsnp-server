package com.herycs.article.service;

import com.netflix.discovery.converters.Auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.article.dao.CollectDao;
import com.herycs.article.pojo.Collect;

import java.util.List;

/**
 * @ClassName CollectService
 * @Description [收藏]
 * @Author ANGLE0
 * @Date 2021/5/3 23:18
 * @Version V1.0
 **/
@Service
public class CollectService {

    @Autowired
    private CollectDao collectDao;

    public List<Collect> findByUid(String uid) {
       return collectDao.findByUidOrderByTimeDesc(uid);
    }

    public void addCollect(String uid, String aid) {
        Collect collect = new Collect();

        collect.setUid(uid);
        collect.setAid(aid);
        collect.setTime(System.currentTimeMillis());
        collect.setState(1);

        collectDao.save(collect);
    }

}
