package com.herycs.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.herycs.article.dao.SourceDao;
import com.herycs.article.pojo.Source;

import java.util.List;

/**
 * @ClassName SourceService
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 14:56
 * @Version V1.0
 **/
@Service
public class SourceService {

    @Autowired
    private SourceDao sourceDao;

    public void addSource(Source source) {
        source.setUploadtime(System.currentTimeMillis());
        sourceDao.save(source);
    }

    public void update(Source source) {
        source.setUpdatetime(System.currentTimeMillis());
        sourceDao.save(source);
    }

    public List<Source> findByUser(String uid) {
        return sourceDao.findByUidAndState(uid, 1);
    }

    public List<Source> search(String name) {
        if (StringUtils.isEmpty(name)) {
            return sourceDao.findAll();
        }
        return sourceDao.findByName(name);
    }

    public Source findById(int id) {
        return sourceDao.findById(id);
    }

}
