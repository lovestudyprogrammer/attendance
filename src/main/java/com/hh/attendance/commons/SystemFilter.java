package com.hh.attendance.commons;

import com.alibaba.fastjson.JSON;
import com.hh.attendance.pojo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: 全局过滤器
 * @FileName: customerdelivery
 * @Author: anthony
 * @Date: 2020/04/12 20:46
 **/
public class SystemFilter implements Filter {

    private static final Log logger = LogFactory.getLog(SystemFilter.class);

    private static List<String> whiteList = Collections.synchronizedList(new ArrayList<String>()); // 白名单

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("执行init初始化程序");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();
        if (ckeckWhiteUrl(servletPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = SessionHolder.getUser();
        if (user == null || user.getId() == null) {
            if (!response.isCommitted()) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/json");
                response.getWriter().print(JSON.toJSONString(ResultBody.failed(3301, "登录已经过期，请重新登录")));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean ckeckWhiteUrl(String url) {
        List<String> whiteList = getWhiteList();
        for (String s : whiteList) {
            if (url.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getWhiteList() {
        if (!whiteList.isEmpty()) {
            return whiteList;
        }
        synchronized (whiteList) {
            if (whiteList.isEmpty()) {
                whiteList.add("/login");
                whiteList.add("/register");
                whiteList.add("/findPassword");
            }
        }
        return whiteList;
    }

    @Override
    public void destroy() {
        logger.info("执行destory方法");
    }
}
