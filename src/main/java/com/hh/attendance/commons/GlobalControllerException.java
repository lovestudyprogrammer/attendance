package com.hh.attendance.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: 全局异常控制器
 * @FileName: customerdelivery
 * @Author: anthony
 * @Date: 2020/04/12 20:46
 **/
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerException {
    private static final Log logger = LogFactory.getLog(GlobalControllerException.class);

    @ExceptionHandler(RuntimeException.class)
    public ResultBody HandlerRuntimeExcepiton(RuntimeException e) {
        logger.error(e);
        String message = e.getMessage();
        if (message.contains(Constant.ERRORMSG)) {
            ResultBody resultBody = new ResultBody(false, 33001, message);
            return resultBody;
        }
        return ResultBody.failed(message);
    }

    @ExceptionHandler(Exception.class)
    public ResultBody HandlerExcepiton(Exception e) {
        logger.error(e);
        return ResultBody.failed("系统异常，请稍等");
    }
}
