package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.herycs.article.pojo.ArticleComment;

import java.util.List;

/**
 * @ClassName ArticleComment
 * @Description [文章评论]
 * @Author ANGLE0
 * @Date 2021/4/27 23:24
 * @Version V1.0
 **/
public interface ArticleCommentDao extends JpaRepository<ArticleComment,String>,JpaSpecificationExecutor<ArticleComment> {
    List<ArticleComment> findByArticleid(String articleid);

    List<ArticleComment> findByUidOrderByCtimeDesc(String uid);
}
