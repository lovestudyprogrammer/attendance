package com.hh.attendance.commons;

import com.hh.attendance.pojo.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: session
 * @FileName: customerdelivery
 * @Author: anthony
 * @Date: 2020/04/12 20:46
 **/
public class SessionHolder {

    public static User getUser() {
        HttpSession session = getSession();
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        return user;
    }

    public static Long getUserId() {
        User user = getUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public static void put(String key, Object value) {
        HttpSession session = getSession();
        session.setAttribute(key, value);
    }

    public static void clearSession() {
        HttpSession session = getSession();
        session.removeAttribute(Constant.USER_SESSION);
        session.invalidate();
    }

    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }
}
