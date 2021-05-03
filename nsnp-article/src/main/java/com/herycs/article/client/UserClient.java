package com.herycs.article.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.herycs.common.entity.Result;

import java.util.List;

/**
 * @ClassName UserClient
 * @Description []
 * @Author ANGLE0
 * @Date 2021/4/27 0:09
 * @Version V1.0
 **/
@Component
@FeignClient("nsnp-user")
public interface UserClient {

    @RequestMapping(value="/user/{id}",method= RequestMethod.GET)
    Result getUserInfoById(@PathVariable("id") String id) ;

    @RequestMapping(value = "/user/utags/{uid}")
    List<String> getUserTags(@PathVariable("uid") String uid);

    @RequestMapping(value = "/user/tag/{uid}/{opr}/{tag}", method = RequestMethod.GET)
    public void updateTag(@PathVariable("uid") String uid, @PathVariable("tag") String tag, @PathVariable("opr") int opr);

}
