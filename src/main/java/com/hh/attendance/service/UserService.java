package com.hh.attendance.service;

import com.hh.attendance.pojo.User;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {
    User getUserById(@RequestParam("userId") Long userId);
}
