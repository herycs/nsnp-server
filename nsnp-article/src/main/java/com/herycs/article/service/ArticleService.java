package com.herycs.article.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herycs.article.constant.Commons;
import com.herycs.article.dao.ArticleDao;
import com.herycs.article.pojo.Article;
import com.herycs.common.util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ArticleService {

    private static Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    public void examine(String id) {
        articleDao.examine(id);
    }

    public void updateThumbup(String id, boolean up) {
        if (up){
            articleDao.updateThumbup(id);
        }
        articleDao.updateThumbdown(id);
    }


    public List<Article> getRecommand(String channelId) {
        return articleDao.findByColumnid(channelId);
    }

    public List<Article> getLifeRecommend(List<String> columnId, String channelId) {

        List<Article> resList = new ArrayList<>();

        for (int i = 0; i < columnId.size(); i++) {

            List<Article> lifeArticle = articleDao.findLifeArticle(channelId, columnId.get(i));

            resList.addAll(Optional.ofNullable(lifeArticle).map(list -> list.subList(0, Math.min(6, list.size()))).orElse(null));
        }

        return resList;
    }

    // 娱乐
    public List<Article> getHappyList(String channelid) {
        return articleDao.getHappyList(channelid,6);
    }

    // 点赞热帖
    public List<Article> getHotList() {
        return articleDao.getHot(6);
    }

    // 用户原创帖
    public int getUserArticle(String uid) {
        return articleDao.getUserWriteArticleNum(uid);
    }

    // 点赞大于50的帖子
    public int getLargetFifty(String uid) {
        return articleDao.getLargerThanFiftyArticle(uid);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, int page, int size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id) {

        //先从缓存中拿数据
        Article article = null;
        try {
             article = (Article) redisTemplate.opsForValue().get("article_" + id);
        } catch (Exception e) {
            logger.info("ArticleService 144 line, redis:操作异常");
        }
        //如果拿不到，就去数据库中查询
        if (article == null) {

            logger.info("aid:{}", id);
            article = articleDao.findById(id).get();
            //放入缓存中
            redisTemplate.opsForValue().set("article_" + id, article, 20, TimeUnit.SECONDS);
        }
        return article;
    }

    public List<Article> findByUid(String uid) {
        return articleDao.findByUseridOrderByUpdatetimeDesc(uid);
    }

    public List<Article> findLifeRecordByUser(String uid, String chanelid) {
        return articleDao.findByUseridAndChannelid(uid, chanelid);
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article) {
        //清除缓存中的数据
        redisTemplate.delete("article_" + article.getId());
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        //清除缓存中的数据
        redisTemplate.delete("article_" + id);
        articleDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {

        return new Specification<Article>() {

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 专栏ID
                if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid"))) {
                    predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + (String) searchMap.get("columnid") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 文章正文
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 文章封面
                if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
                }
                // 是否公开
                if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic"))) {
                    predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + (String) searchMap.get("ispublic") + "%"));
                }
                // 是否置顶
                if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop"))) {
                    predicateList.add(cb.like(root.get("istop").as(String.class), "%" + (String) searchMap.get("istop") + "%"));
                }
                // 审核状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                // 所属频道
                if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid"))) {
                    predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + (String) searchMap.get("channelid") + "%"));
                }
                // URL
                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }
                // 类型
                if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    public List<Article> findByGroupId(String id) {
        return articleDao.findGroupArticle(id);
    }

    public List<Article> findHotByGroupId(String id) {
        return articleDao.findHotByGroupId(id);
    }

}
