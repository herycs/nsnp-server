package com.herycs.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;
import com.herycs.user.pojo.Message;
import com.herycs.user.pojo.User;
import com.herycs.user.service.MessageService;
import com.herycs.user.service.UserService;

import java.util.*;

/**
 * @ClassName ChatController
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/29 0:15
 * @Version V1.0
 **/

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class ChatController {

    private static Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/record/{uid}/{receptorId}", method = RequestMethod.GET)
    public Result getRecordByUser(@PathVariable("uid") String uid, @PathVariable("receptorId") String receptorId) {

        List<Message> record = messageService.getRecord(uid, receptorId);

        ArrayList<Map> resList = new ArrayList<>();

        record.forEach(item -> {

            HashMap<String, Object> map = new HashMap<>();
            User userInfo = userService.findById(item.getSender());

            map.put("uid", userInfo.getId());
            map.put("username", Optional.ofNullable(userInfo.getNickname()).orElse(userInfo.getMobile()));
            map.put("avatar", userInfo.getAvatar());
            map.put("chatData", item.getMsg());
            map.put("time", item.getTime());

            resList.add(map);
        });

        return new Result(true, StatusCode.OK, "获取成功", resList);
    }

    @RequestMapping(value = "/list/{uid}", method = RequestMethod.GET)
    public Result getAllReceptors(@PathVariable("uid") String uid) {

        List<String> allReceptor = messageService.getAllReceptor(uid);

        logger.info("{}", allReceptor);

        List<Map> resList = new ArrayList<>();

        allReceptor.forEach(item -> {

            HashMap<String, String> chatInfoMap = new HashMap<>();

            User userInfo = userService.findById(item);


            chatInfoMap.put("uid", userInfo.getId());
            chatInfoMap.put("username", userInfo.getNickname());
            chatInfoMap.put("summary", userInfo.getInterest());
            chatInfoMap.put("avatar", userInfo.getAvatar());
            Message lastMessage = messageService.getLastMessage(uid, item);
            chatInfoMap.put("lastMessage", lastMessage.getMsg());
            chatInfoMap.put("time", lastMessage.getTime());

            resList.add(chatInfoMap);

        });


        return new Result(true, StatusCode.OK, "获取成功", resList);
    }


}
