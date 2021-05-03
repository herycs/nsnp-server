package com.herycs.article.controller;


import com.google.common.collect.Lists;
import com.netflix.discovery.converters.Auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.herycs.article.client.UserClient;
import com.herycs.article.constant.Commons;
import com.herycs.article.dao.ArticleDao;
import com.herycs.article.pojo.Article;
import com.herycs.article.pojo.ArticleComment;
import com.herycs.article.pojo.Log;
import com.herycs.article.service.ArticleCommentService;
import com.herycs.article.service.ArticleService;
import com.herycs.article.service.ColumnService;
import com.herycs.article.service.LogService;
import com.herycs.common.entity.PageResult;
import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;


import java.util.*;

/**
 * 控制器层
 * @author Administrator
 *
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
	private LogService logService;

	@Autowired
	private ArticleCommentService articleCommentService;

	@RequestMapping(value = "/examine/{articleId}", method= RequestMethod.PUT)
	public Result examine(@PathVariable String articleId){
		articleService.examine(articleId);
		return new Result(true, StatusCode.OK,"操作成功");
	}

	@RequestMapping(value = "/thumbup/{articleId}", method= RequestMethod.GET)
	public Result updateThumbup(@PathVariable String articleId){
		articleService.updateThumbup(articleId, true);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	@RequestMapping(value = "/thumbdown/{articleId}", method= RequestMethod.GET)
	public Result updateThumbdown(@PathVariable String articleId){
		Article article = articleService.findById(articleId);
		if (article != null && article.getThumbdown() > 0) {
			articleService.updateThumbup(articleId, false);
		}
		return new Result(true,StatusCode.OK,"操作成功");
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){

		List<Map> articleList = Lists.newArrayList();

		List<Article> all = articleService.findAll();

		all.forEach(item -> {

			logger.info("userId:{}, userInfo:[{}]", item.getUserid(), userClient.getUserInfoById(item.getUserid()).toString());

			HashMap<String, Object> articleMap = new HashMap<>();
			articleMap.put("userInfo", userClient.getUserInfoById(item.getUserid()).getData());
			articleMap.put("articleInfo", item);

			articleList.add(articleMap);
		});


		return new Result(true,StatusCode.OK,"查询成功", articleList);
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){

		Article article = articleService.findById(id);

		// 增加频道访问量
		columnService.addVisited(article.getColumnid());

		// 增加用户访问日志
		logService.addLog(article);

		// 更新标签统计
		userClient.updateTag(article.getUserid(), article.getColumnid(), Commons.ARTICLE_POR_VISIT);

		HashMap<String, Object> articleMap = new HashMap<>();
		articleMap.put("userInfo",
				Optional.ofNullable(userClient.getUserInfoById(article.getUserid()))
					.map(item -> item.getData())
					.orElse(null));
		articleMap.put("articleInfo", article);


		return new Result(true,StatusCode.OK,"查询成功", articleMap);
	}

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Article> pageList = articleService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Article article){
		articleService.add(article);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.update(article);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		articleService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	@RequestMapping(value = "/recommand/{uid}", method = RequestMethod.GET)
	public Result getReommand(@PathVariable("uid") String uid) {

		List<Article> resList = new ArrayList<>();

		List<String> userTags = userClient.getUserTags(uid);
		for (int i = 0; i < userTags.size(); i++) {
			List<Article> recommand = articleService.getRecommand(userTags.get(i));
			resList.addAll(recommand.subList(0, 3));
		}


		return new Result(true, StatusCode.OK, "刷新成功", resList);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Result addComment(@RequestBody ArticleComment articleComment) {
		articleCommentService.add(articleComment);

		Article article = articleService.findById(articleComment.getId());
		if (article != null) {
			userClient.updateTag(article.getUserid(), article.getChannelid(), Commons.ARTICLE_POR_COMMENT);
		}

		return new Result(true,StatusCode.OK,"添加成功");
	}

	@RequestMapping(value = "/comment/{articleId}", method = RequestMethod.GET)
	public Result findAllComments(@PathVariable("articleId") String articleId) {

		List<ArticleComment> comments = articleCommentService.findByArticleId(articleId);

		List<Map> res = new ArrayList<>();

		comments.forEach(item -> {
			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("userInfo", userClient.getUserInfoById(item.getUid()).getData());
			resMap.put("comment", item);
			res.add(resMap);
		});

		return new Result(true, StatusCode.OK, "获取成功", res);
	}

}
