package com.herycs.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addLog(Article article) {

        Log log = new Log();

        log.setAid(article.getId());
        log.setColumnid(article.getColumnid());
        log.setChannelid(article.getColumnid());
        log.setTime(System.currentTimeMillis());

        logDao.save(log);
    }

    public List<Log> findByUid(String uid) {
        return logDao.findByUid(uid);
    }

}
