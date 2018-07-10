package com.muti.service;

import com.muti.annotation.DS;
import com.muti.dao.CustomerDao;
import com.muti.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/7/10 13:38
 * UpdateTime 2018/7/10 13:38
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomerDao customerDao;

    @DS("datasource2")
    public List<Map> getAllUser() {
        return userDao.getAllUser();
    }

    @DS("datasource1")
    public List<Map> getAllCustomer() {
        return customerDao.getAllCustomer();
    }
}
