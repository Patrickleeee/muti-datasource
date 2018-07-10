package com.muti.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/7/10 13:41
 * UpdateTime 2018/7/10 13:41
 */
@Mapper
public interface CustomerDao {

    public List<Map> getAllCustomer();
}
