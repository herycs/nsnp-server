package com.herycs.article.controller;


import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.herycs.article.client.UserClient;
import com.herycs.article.constant.Commons;
import com.herycs.article.pojo.Article;
import com.herycs.article.pojo.ArticleComment;
import com.herycs.article.pojo.Collect;
import com.herycs.article.pojo.Log;
import com.herycs.article.service.*;
import com.herycs.common.entity.PageResult;
import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;
import com.herycs.user.pojo.User;


import java.util.*;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private UserClient userClient;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private LogService logService;

    @Autowired
    private ArticleCommentService articleCommentService;

    private static final String CHANNEL_LIFE_RECORD = "1386686506122481664";
    private static final String CHANNEL_HAPPY = "1386686506122481665";

    @RequestMapping(value = "/examine/{articleId}", method = RequestMethod.PUT)
    public Result examine(@PathVariable String articleId) {
        articleService.examine(articleId);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {

        List<Map> articleList = Lists.newArrayList();

        List<Article> all = articleService.findAll();

        all.forEach(item -> {

            logger.info("userId:{}, userInfo:[{}]", item.getUserid(), userClient.findById(item.getUserid()).toString());

            HashMap<String, Object> articleMap = new HashMap<>();
            articleMap.put("userInfo", userClient.findById(item.getUserid()).getData());
            articleMap.put("articleInfo", item);

            articleList.add(articleMap);
        });


        return new Result(true, StatusCode.OK, "查询成功", articleList);
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/comment/{articleId}", method = RequestMethod.GET)
    public Result findAllComments(@PathVariable("articleId") String articleId) {

        List<ArticleComment> comments = articleCommentService.findByArticleId(articleId);

        List<Map> res = new ArrayList<>();

        comments.forEach(item -> {
            HashMap<String, Object> resMap = new HashMap<String, Object>();

            resMap.put("userInfo", userClient.findById(item.getUid()).getData());
            resMap.put("comment", item);
            res.add(resMap);
        });

        return new Result(true, StatusCode.OK, "获取成功", res);
    }

    // 和用户操作敏感

    @RequestMapping(value = "/record/write/{uid}", method = RequestMethod.GET)
    public Result getUserWriteCount(@PathVariable("uid") String uid) {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("userArticleCount", articleService.getUserArticle(uid));
        map.put("thumbupLargerCount", articleService.getLargetFifty(uid));

        return new Result(true, StatusCode.OK, "获取纪录成功", map);
    }

    /**
     * 获取用户发帖纪录
     */
    @RequestMapping(value = "/record/{uid}", method = RequestMethod.GET)
    public Result findByUid(@PathVariable("uid") String uid) {

        ArrayList<Map> maps = Lists.newArrayList();

        articleService.findByUid(uid).forEach(article -> {
            Map<String, String> userInfo = (Map<String, String>) userClient.findById(article.getUserid()).getData();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("userInfo", userInfo);
            map.put("articleInfo", article);

            maps.add(map);
        });

        return new Result(true, StatusCode.OK, "获取纪录成功", maps);
    }

    /**
     * 获取用户生活贴分享
     */
    @RequestMapping(value = "/life/{uid}", method = RequestMethod.GET)
    public Result findLifeRecordByUid(@PathVariable("uid") String uid) {

        return new Result(true, StatusCode.OK, "获取成功", articleService.findLifeRecordByUser(uid, CHANNEL_LIFE_RECORD));
    }

    /**
     * 获取用户互动过的帖子
     */
    @RequestMapping(value = "/comm/{uid}", method = RequestMethod.GET)
    public Result findInterest(@PathVariable("uid") String uid) {

        List<Log> byUid = logService.findByUid(uid, Commons.ARTICLE_POR_THUMB);

        ArrayList<Article> articles = new ArrayList<>();

        for (int i = 0; i < byUid.size(); i++) {
            articles.add(articleService.findById(byUid.get(i).getAid()));
        }


        return new Result(true, StatusCode.OK, "获取成功", articles);
    }

    /**
     * 获取用户收藏记录
     */
    @RequestMapping(value = "/collect/{uid}", method = RequestMethod.GET)
    public Result findUserCollect(@PathVariable("uid") String uid) {

        List<Collect> collectList = collectService.findByUid(uid);

        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < collectList.size(); i++) {
            articles.add(articleService.findById(collectList.get(i).getAid()));
        }


        return new Result(true, StatusCode.OK, "获取成功", articles);
    }

    @RequestMapping(value = "/collect/{aid}/{uid}", method = RequestMethod.GET)
    public Result addUserCollect(@PathVariable("aid") String aid, @PathVariable("uid") String uid) {

        collectService.updateCollect(uid, aid);

        return new Result(true, StatusCode.OK, "增加收藏成功");
    }

    /**
     * 获取用户访问记录
     */
    @RequestMapping(value = "/history/{uid}", method = RequestMethod.GET)
    public Result findUserHistory(@PathVariable("uid") String uid) {
        List<Log> logList = logService.findByUid(uid, Commons.ARTICLE_POR_VISIT);

        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < logList.size(); i++) {
            articles.add(articleService.findById(logList.get(i).getAid()));
        }

        return new Result(true, StatusCode.OK, "获取成功", articles);
    }

    /**
     * 评论列表
     */
    @RequestMapping(value = "/rc/{uid}", method = RequestMethod.GET)
    public Result findUserComment(@PathVariable("uid") String uid) {
        return new Result(true, StatusCode.OK, "获取成功", articleCommentService.findUserComment(uid));
    }

    /**
     * 获取文章详情
     */
    @RequestMapping(value = "/{id}/{uid}", method = RequestMethod.GET)
    public Result findById(@PathVariable("uid") String uid, @PathVariable("id") String id) {

        Article article = articleService.findById(id);

        // 增加频道访问量
        columnService.addVisited(article.getColumnid());

        // 增加用户访问日志
        logService.addVisit(uid, article);

        // 更新标签统计
        userClient.updateTag(uid, article.getColumnid(), Commons.ARTICLE_POR_VISIT);

        HashMap<String, Object> articleMap = new HashMap<>();
        articleMap.put("userInfo",
                Optional.ofNullable(userClient.findById(article.getUserid()))
                        .map(item -> item.getData())
                        .orElse(null));
        articleMap.put("articleInfo", article);

        return new Result(true, StatusCode.OK, "查询成功", articleMap);
    }


    /**
     * 获取推荐
     */
    @RequestMapping(value = "/recommend/{uid}", method = RequestMethod.GET)
    public Result getRecommend(@PathVariable("uid") String uid) {

        List<Article> resList = new ArrayList<>();
        List<String> columnId = userClient.getUserTag(uid); // 用户感兴趣的标签
        logger.info("user tag: {}", columnId);
        for (int i = 0; i < columnId.size(); i++) {
            List<Article> recommand = articleService.getRecommand(columnId.get(i));
            logger.info("{}", recommand);
            resList.addAll(recommand.subList(0, Math.min(2, recommand.size())));
        }

        if (resList == null || resList.size() == 0) {
            resList = articleService.findAll();
        }

        List<Article> happyList = articleService.getHappyList(CHANNEL_HAPPY);
        List<Article> hotList = articleService.getHotList();

        HashMap<String, List> resMap = new HashMap<>();

        if (resList.size() > 3) {
            resList = resList.subList(0, 3);
        }
        resList.forEach(article -> { // 避免重复推荐
            article.setState("2");
            articleService.update(article);
        });


        resMap.put("recommend", parseArticle(resList));

        resMap.put("happyList", parseArticle(happyList));

        resMap.put("hotList", parseArticle(hotList));

        return new Result(true, StatusCode.OK, "刷新成功", resMap);
    }

    private List<Map> parseArticle(List<Article> list) {

        ArrayList<Map> maps = new ArrayList<>();

        list.forEach(item -> {

            HashMap<String, Object> resMap = new HashMap<>();
            resMap.put("userInfo", userClient.findById(item.getUserid()).getData());
            resMap.put("articleInfo", item);
            maps.add(resMap);
        });
        return maps;
    }

    /**
     * 获取生活贴推荐
     */
    @RequestMapping(value = "/life/{start}/{uid}", method = RequestMethod.GET)
    public Result getRecommendLifeArticle(@PathVariable("uid") String uid, @PathVariable("start") int start) {

        List<String> userTags = userClient.getUserTag(uid);

        List<String> tags = userTags.subList(0, Math.min(userTags.size(), 10));

        return new Result(true, StatusCode.OK, "获取生活贴推荐成功", articleService.getLifeRecommend(tags, CHANNEL_LIFE_RECORD));
    }

    /**
     * 点赞
     */
    @RequestMapping(value = "/thumbup/{articleId}/{uid}", method = RequestMethod.GET)
    public Result updateThumbup(@PathVariable("uid") String uid, @PathVariable("articleId") String articleId) {
        articleService.updateThumbup(articleId, true);

        Article byId = articleService.findById(articleId);

        userClient.updateTag(uid, byId.getColumnid(), Commons.ARTICLE_POR_THUMB);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 点踩
     */
    @RequestMapping(value = "/thumbdown/{articleId}", method = RequestMethod.GET)
    public Result updateThumbdown(@PathVariable String articleId) {
        Article article = articleService.findById(articleId);
        if (article != null && article.getThumbdown() > 0) {
            articleService.updateThumbup(articleId, false);
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 更新用户标签
     */

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Result addComment(@RequestBody ArticleComment articleComment) {

        Article byId = articleService.findById(articleComment.getArticleid());
        byId.setComment(byId.getComment() + 1);
        articleService.update(byId);

        articleCommentService.add(articleComment);

        Article article = articleService.findById(articleComment.getArticleid());
        if (article != null) {
            userClient.updateTag(article.getUserid(), article.getColumnid(), Commons.ARTICLE_POR_COMMENT);
        }

        return new Result(true, StatusCode.OK, "添加成功");
    }

}
