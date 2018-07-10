package com.muti.controller;

import com.muti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/7/10 13:43
 * UpdateTime 2018/7/10 13:43
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/db1")
    public List<Map> getAllCustomer() {
        return userService.getAllCustomer();
    }

    @GetMapping("db2")
    public List<Map> getAllUser() {
        return userService.getAllUser();
    }
}
