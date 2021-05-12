package com.herycs.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import com.herycs.user.pojo.UserTag;
import com.herycs.user.service.UserTagService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName TagController
 * @Description []
 * @Author ANGLE0
 * @Date 2021/5/3 16:09
 * @Version V1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private UserTagService userTagService;

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public List getUserTag(@PathVariable("uid") String uid) {
        List<UserTag> userTags = userTagService.findUserTags(uid);

        List<String> tagList = Optional.ofNullable(userTags)
                .map(tags -> tags.stream().map(UserTag::getColumnid).collect(Collectors.toList()))
                .orElse(null);

        return tagList;
    }

    @RequestMapping(value = "/{uid}/{opr}/{tag}", method = RequestMethod.GET)
    public void updateTag(@PathVariable("uid") String uid, @PathVariable("tag") String collumnId, @PathVariable("opr") int opr) {


        UserTag byTag = userTagService.findByTag(uid, collumnId);
        if (byTag == null) {
            userTagService.addTag(uid, collumnId);
        }

        userTagService.update(uid, collumnId, opr);
    }

}
