package com.herycs.nsnpchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.herycs.nsnpchat.model.MessageDto;
import com.herycs.nsnpchat.service.MessageService;

/**
 * @ClassName CommunicateController
 * @Description [聊天处理类]
 * @Author ANGLE0
 * @Date 2021/4/24 17:39
 * @Version V1.0
 **/
@Controller
public class CommunicateController {

    private Logger logger = LoggerFactory.getLogger(CommunicateController.class);

    @Autowired
    private MessageService messageService;

    @Lazy
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final String MESSAGE_TYPE_NOTICE = "TO_PUBLIC";

    private static final String MESSAGE_TYPE_USER = "TO_USER";


    @MessageMapping("/all")
    @SendTo("/topic/public")
    public MessageDto broadCast(@Payload MessageDto messageDto) {

        logger.info("to broadCast, {}", messageDto);

        messageDto.setType(MESSAGE_TYPE_NOTICE);

        messageService.addRecord(messageDto);

        return new MessageDto("public", "广播消息");
    }


    @MessageMapping("/chat")
    public void toUser(@Payload MessageDto messageDto) {

        messageDto.setType(MESSAGE_TYPE_USER);

        messageService.addRecord(messageDto);
        logger.info("{}", messageDto);

        messagingTemplate.convertAndSend("/queue/" + messageDto.getReceptor(), messageDto);
    }


    @MessageMapping("/room")
    public void toRoom(@Payload MessageDto messageDto) {

        logger.info("to public {}", messageDto);
        messageDto.setType("TO_ROOM");

        messageService.addRecord(messageDto);
        MessageDto m = new MessageDto("public", "来自群聊的消息");

        messagingTemplate.convertAndSend("/topic/room/" + messageDto.getReceptor(), m);
    }

}
