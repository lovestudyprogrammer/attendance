package com.hh.attendance.commons;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description:
 * @FileName: CommonUtil
 * @Author: anthony
 * @Date: 2020/04/12 20:46
 **/
public class CommonUtil {

    public static void ckeckAugrmentIsNull(Object o, String msg) {
        if (o == null) {
            throw new RuntimeException(msg);
        }
        if (o instanceof String) {
            if (StringUtils.isEmpty((String) o)) {
                throw new RuntimeException(msg);
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) <= 0) {
                throw new RuntimeException(msg);
            }
        }
    }

    public static int generateNumber() {
        SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMdd"); // 获取当前时间

        Random random = new Random(); // 定义随机数
        int ran = random.nextInt(100); // 随机数长度位数

        Date date = new Date();
        String dateran = dmDate.format(date);
        String randomMath = dateran + ran;
        return Integer.valueOf(randomMath);
    }
}
