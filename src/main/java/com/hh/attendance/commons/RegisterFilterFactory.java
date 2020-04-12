package com.hh.attendance.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description:
 * @FileName: customerdelivery
 * @Author: anthony
 * @Date: 2020/04/12 20:46
 **/
@Configuration
public class RegisterFilterFactory {

    private static final Log logger = LogFactory.getLog(SystemFilter.class);

    @Bean
    public FilterRegistrationBean registSystemFilter() {
        logger.info("开始注册systemFilter过滤器");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        SystemFilter systemFilter = new SystemFilter();
        filterRegistrationBean.setFilter(systemFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("systemFilter");
        logger.info("注册systemFilter过滤器完成");
        return filterRegistrationBean;
    }
}
