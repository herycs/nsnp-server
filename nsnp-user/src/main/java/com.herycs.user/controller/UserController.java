package com.herycs.user.controller;

import io.jsonwebtoken.lang.Collections;

import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.herycs.common.entity.PageResult;
import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;
import com.herycs.common.util.JwtUtil;
import com.herycs.user.pojo.Message;
import com.herycs.user.pojo.User;
import com.herycs.user.pojo.UserTag;
import com.herycs.user.service.FriendService;
import com.herycs.user.service.MessageService;
import com.herycs.user.service.UserService;
import com.herycs.user.service.UserTagService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String NOTICE = "SYSTEM";

    /**
     * 更新好友粉丝数和用户关注数
     *
     * @return
     */
    @RequestMapping(value = "/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    public void updatefanscountandfollowcount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x) {
        userService.updatefanscountandfollowcount(x, userid, friendid);
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {

        user = userService.login(user.getMobile(), user.getPassword());

        if (user == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }

        return new Result(true, StatusCode.OK, "登录成功", createUserInfo(user));
    }

    /*
        基于用户信息创建用户Token
     */
    private Map createUserInfo(User user) {
        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
        Map<String, Object> map = new HashMap<>();
        map.put("uid", user.getId());
        map.put("username", Optional.ofNullable(user.getNickname()).orElse(user.getMobile()));
        map.put("avatar", Optional.ofNullable(user.getAvatar()));
        map.put("token", token);
        map.put("roles", "user");
        return map;
    }

    /**
     * 发送短信验证码
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 发送并返回本次验证码
     */
    @RequestMapping(value = "/sendback/{mobile}", method = RequestMethod.POST)
    public Result sendAndGetCode(@PathVariable String mobile) {
        return new Result(true, StatusCode.OK, "发送成功", userService.sendSms(mobile));
    }

    /**
     * 注册
     *
     * @return
     */
    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result regist(@PathVariable String code, @RequestBody User user) {
        //得到缓存中的验证码
        User byPhone = userService.findByPhone(user.getMobile());
        if (byPhone != null) {
            return new Result(true, StatusCode.ERROR, "手机号已被注册");
        }
//		String checkcodeRedis = (String) redisTemplate.opsForValue().get("checkcode_" + user.getMobile());
//		if(checkcodeRedis.isEmpty()){
//			return new Result(false, StatusCode.ERROR, "请先获取手机验证码");
//		}
//		if(!checkcodeRedis.equals(code)){
//			return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
//		}


        userService.add(user);

        User userInfo = userService.findByPhone(user.getMobile());

        Message message = new Message();
        message.setReceptor(userInfo.getId());
        message.setMsg("欢迎来到 星空社交平台，有问题可以联系我，也可以通过客服帮助找到我");
        message.setTime(new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));
        message.setType(NOTICE);
        messageService.addRecord(message);

        return new Result(true, StatusCode.OK, "注册成功", createUserInfo(userInfo));
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {


        User byId = userService.findById(id);
        logger.info("userId: {}, user: {}", id, byId);

        HashMap<String, Object> userInfo = new HashMap<>();

        userInfo.put("id", byId.getId());
        userInfo.put("avatar", byId.getAvatar());
        userInfo.put("nickName", Optional.ofNullable(byId.getNickname()).orElse(byId.getMobile()));
        userInfo.put("interest", byId.getInterest());
        userInfo.put("thubmCount", 174);
        userInfo.put("likeCount", friendService.getLikeCount(byId.getId(), 1));
        userInfo.put("funCount", friendService.getFunCount(byId.getId(), 1));

        return new Result(true, StatusCode.OK, "查询成功", userInfo);
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
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {

        if (StringUtils.isEmpty(user.getInterest())) {
            user.setInterest("这个用户很神秘，什么都没有写~");
        }

        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/pass/{code}", method = RequestMethod.POST)
    public Result update(@RequestBody User user, @PathVariable("code") String code) {

        logger.info("code:{}， user phone:{}", code, user.getMobile());

        if (!userService.checkCode(code, user.getMobile())) {
            return new Result(true, StatusCode.CHECK_CODE_ERROR, "验证码有误");
        }

        userService.update(user);

        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");

        HashMap<String, String> resMap = new HashMap<>();
        resMap.put("token", token);

        return new Result(true, StatusCode.OK, "修改成功", resMap);
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateUserInfo(@RequestBody User user) {

        Map map = checkUesrInfo(user);
        if (!map.get("code").equals("200")) {
            return new Result(true, StatusCode.OK, map.get("msg").toString());
        }

        userService.updateUserInfo(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除 必须有admin角色才能删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private Map checkUesrInfo(User user) {

        String emailReg = "^\\w+([-_.]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$";

        String phoneReg = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

        String msg = null;
        if (!user.getMobile().matches(phoneReg)) {
            msg = "手机号格式不合法";
        }

        if (!user.getEmail().matches(emailReg)) {
            msg = "邮箱格式不合法";
        }

        HashMap<String, String> resMap = new HashMap<>();

        resMap.put("code", "200");
        resMap.put("msg", msg);

        return resMap;

    }

}
