package com.herycs.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herycs.article.dao.ArticleCommentDao;
import com.herycs.article.pojo.ArticleComment;
import com.herycs.common.util.IdWorker;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName ArticleCommentService
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/27 23:25
 * @Version V1.0
 **/
@Service
public class ArticleCommentService {

    @Autowired
    private ArticleCommentDao articleCommentDao;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private IdWorker idWorker;

    public List<ArticleComment> findByArticleId(String articleId) {
        return articleCommentDao.findByArticleid(articleId);
    }

    public void add(ArticleComment articleComment) {
        articleComment.setId(idWorker.nextId() + "");

        articleComment.setCtime(dateFormat.format(System.currentTimeMillis()));

        articleCommentDao.save(articleComment);
    }

    public List<ArticleComment> findUserComment(String uid) {
        return articleCommentDao.findByUidOrderByCtimeDesc(uid);
    }

}
