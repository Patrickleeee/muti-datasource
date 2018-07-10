package com.muti.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/7/10 13:33
 * UpdateTime 2018/7/10 13:33
 */
@Accessors(chain = true)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
     private long id;
     private String username;
     private String password;
}
