package org.malred.service.pojo;


import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测试用实体类
 */
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    private String name;
    private String password;
    private String address;
    private String phone;
    // 需要重写tostring,包含上父类的属性
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
