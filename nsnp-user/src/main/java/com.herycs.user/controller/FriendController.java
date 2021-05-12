package com.herycs.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;
import com.herycs.user.pojo.Friend;
import com.herycs.user.pojo.User;
import com.herycs.user.service.FriendService;
import com.herycs.user.service.UserService;

import java.util.*;

/**
 * @ClassName FriendController
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/4 1:04
 * @Version V1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/friends/{uid}", method = RequestMethod.GET)
    public Result getFriendList(@PathVariable("uid") String uid) {
        return new Result(true, StatusCode.OK, "获取好友列表成功", parseUserList(friendService.findUserFriend(uid)));
    }

    @RequestMapping(value = "/fun/{uid}", method = RequestMethod.GET)
    public Result getFunList(@PathVariable("uid") String uid) {
        return new Result(true, StatusCode.OK, "获取粉丝列表成功", parseUserList(friendService.findLikeUser(uid)));
    }

    @RequestMapping(value = "/like/{uid}", method = RequestMethod.GET)
    public Result getLikeList(@PathVariable("uid") String uid) {
        return new Result(true, StatusCode.OK, "获取关注列表成功", parseUserList(friendService.findUserLike(uid)));
    }

    private List<Map> parseUserList(List<Friend> friendList) {

        if (friendList == null || friendList.size() <= 0) {
            return null;
        }

        ArrayList<Map> maps = new ArrayList<>();

        for (int i = 0; i < friendList.size(); i++) {
            User byId = userService.findById(friendList.get(i).getFriendid());

            HashMap<String, String> userMap = new HashMap<>();
            userMap.put("id", byId.getId());
            userMap.put("avatar", byId.getAvatar());
            userMap.put("nickName", Optional.ofNullable(byId.getNickname()).orElse(byId.getMobile()));
            userMap.put("summary", byId.getInterest());

            maps.add(userMap);
        }

        return maps;
    }

    @RequestMapping(value = "/add/{uid}/{friendId}", method = RequestMethod.GET)
    public Result addFriend(@PathVariable("uid") String uid, @PathVariable("friendId") String friendId) {
        friendService.updateFriend(uid, friendId, 1);
        return new Result(true, StatusCode.OK, "获取好友列表成功");
    }

    @RequestMapping(value = "/del/{uid}/{friendId}", method = RequestMethod.GET)
    public Result deleteFriend(@PathVariable("uid") String uid, @PathVariable("friendId") String friendId) {
        friendService.updateFriend(uid, friendId, 0);
        return new Result(true, StatusCode.OK, "获取好友列表成功");
    }

}
