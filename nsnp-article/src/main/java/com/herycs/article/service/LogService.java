package com.herycs.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.article.constant.Commons;
import com.herycs.article.dao.LogDao;
import com.herycs.article.pojo.Article;
import com.herycs.article.pojo.Log;

import java.util.List;

/**
 * @ClassName LogService
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/29 13:19
 * @Version V1.0
 **/
@Service
public class LogService {

    @Autowired
    private LogDao logDao;

    public void addVisit(String uid, Article article) {

        Log log = new Log();

        log.setUid(uid);
        log.setAid(article.getId());
        log.setColumnid(article.getColumnid());
        log.setChannelid(article.getColumnid());
        log.setTime(System.currentTimeMillis());
        log.setType(Commons.ARTICLE_POR_VISIT);

        logDao.save(log);
    }

    public List<Log> findByUid(String uid, int type) {
        return logDao.findByUidAndTypeOrderByTimeDesc(uid, type);
    }

}
