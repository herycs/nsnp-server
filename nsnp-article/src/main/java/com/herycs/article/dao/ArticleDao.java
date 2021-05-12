package com.herycs.article.dao;

import com.herycs.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Modifying
    @Query(value = "UPDATE tb_article SET state = 1 WHERE id = ?", nativeQuery = true)
    public void examine(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup = thumbup+1 WHERE id = ?", nativeQuery = true)
    public void updateThumbup(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbdown = thumbdown+1 WHERE id = ?", nativeQuery = true)
    public void updateThumbdown(String id);

    List<Article> findByColumnid(String columnid);

    @Modifying
    @Query(value = "select * from tb_article where channelid=? and columnid=?", nativeQuery = true)
    List<Article> findLifeArticle(String channelId, String columnid);

    List<Article> findByUseridAndChannelid(String uid, String channelId);

    List<Article> findByUseridOrderByUpdatetimeDesc(String uid);

    // 获取圈子内部帖子
    @Query(value = "select * from tb_article where columnid=? order by createtime desc", nativeQuery = true)
    List<Article> findGroupArticle(String columnId);

    // 获取圈子内部帖子
    @Query(value = "select * from tb_article where columnid=? order by thumbup desc", nativeQuery = true)
    List<Article> findHotByGroupId(String columnId);

    // 点赞热帖
    @Query(value = "select * from tb_article order by thumbup desc limit ?", nativeQuery = true)
    List<Article> getHot(int limit);

    // 点赞热帖
    @Query(value = "select * from tb_article where channelid=? order by thumbup desc limit ?", nativeQuery = true)
    List<Article> getHappyList(String channelid, int limit);

    @Query(value = "select count(*) from tb_article where userid=?", nativeQuery = true)
    int getUserWriteArticleNum(String uid);

    @Query(value = "select count(*) from tb_article where userid=? and thumbup>50", nativeQuery = true)
    int getLargerThanFiftyArticle(String uid);
}
