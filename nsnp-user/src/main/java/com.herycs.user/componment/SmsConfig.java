package com.herycs.user.componment;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @ClassName SmsConfig
 * @Description [ 消息队列配置]
 * @Author ANGLE0
 * @Date 2021/3/11 23:58
 * @Version V1.0
 **/
@Component
public class SmsConfig {

    public static final String PHONE_CODE = "sms";

    @Bean
    public Queue initSMSQueue() {
        return new Queue(PHONE_CODE, true);
    }

}
