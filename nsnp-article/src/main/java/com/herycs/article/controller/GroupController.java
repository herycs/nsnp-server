package com.herycs.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.herycs.article.pojo.Article;
import com.herycs.article.pojo.Column;
import com.herycs.article.pojo.Group;
import com.herycs.article.service.ArticleService;
import com.herycs.article.service.ColumnService;
import com.herycs.article.service.GroupService;
import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName GroupController
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/5 0:47
 * @Version V1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ColumnService columnService;

    // 【加入 | 离开 | 访问 】圈子
    //    2      0     1
    @RequestMapping(value = "/{uid}/{gid}/{opr}", method = RequestMethod.GET)
    public Result inGroup(@PathVariable("uid") String uid, @PathVariable("gid") String gid, @PathVariable("opr") int type) {

        groupService.addGroup(uid, gid, type);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    // 获取用户 [加入 | 访问过 ] 的圈子
    //          2         1
    @RequestMapping(value = "/list/{uid}/{opr}", method = RequestMethod.GET)
    public Result getGroupList(@PathVariable("uid") String uid, @PathVariable("gid") String gid, @PathVariable("opr") int type) {

        List<Group> groupList = groupService.findGroupList(uid, type);

        ArrayList<Column> resList = new ArrayList<>();
        for (int i = 0; i < groupList.size(); i++) {
            resList.add(columnService.findById(groupList.get(i).getColumnid()));
        }

        return new Result(true, StatusCode.OK, "操作成功", resList);
    }

    // 获取圈子详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findByGroupId(@PathVariable("id") String columnId) {

        List<Article> newArticleList = articleService.findByGroupId(columnId);
        List<Article> hotList = articleService.findHotByGroupId(columnId);

        HashMap<String, List> resMap = new HashMap<>();
        resMap.put("baseInfo", Arrays.asList(columnService.findById(columnId)));
        resMap.put("newList", newArticleList);
        resMap.put("hotList", hotList);


        return new Result(true, StatusCode.OK, "获取成功", resMap);
    }


}
