package org.malred.service.pojo;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 测试用实体类
 */
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;
    private String name;
    private String password;
    private String address;
    private String phone;
    @Transient
    private String createTime; // 创建时间
    @Transient
    private String updateTime; // 更新时间
}
