package com.muti.bean.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Desciption 分页查询拦截器实体类
 * Create By  li.bo
 * CreateTime 2018/10/17 17:07
 * UpdateTime 2018/10/17 17:07
 */
@Setter
@Getter
public class PageDto<T>{

        private Integer rows=10;

        private Integer offset=0;

        private Integer pageNo=1;

        private Integer totalRecord=0;

        private Integer totalPage=1;

        private Boolean hasPrevious=false;

        private Boolean hasNext=false;

        private Date start;

        private Date end;

        private T searchCondition;

        private List<T> dtos;
        }
