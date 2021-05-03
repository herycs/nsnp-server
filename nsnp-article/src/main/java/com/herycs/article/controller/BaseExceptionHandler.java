package com.herycs.article.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;


/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        if (e.getMessage().equals("No value present")){
            return new Result(true, StatusCode.ERROR, "目标值不存在");
        }
        return new Result(false, StatusCode.ERROR, "执行出错");
    }
}
