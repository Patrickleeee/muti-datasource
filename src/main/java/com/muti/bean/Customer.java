package com.muti.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/7/10 13:35
 * UpdateTime 2018/7/10 13:35
 */
@Accessors(chain = true)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Customer {

    private String customerId;
    private String userId;
    private String celphone;
}
