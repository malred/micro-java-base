package org.malred.service.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 基础实体类
 */
@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    public Long id;
    public String createTime; // 创建时间
    public String updateTime; // 更新时间
}
