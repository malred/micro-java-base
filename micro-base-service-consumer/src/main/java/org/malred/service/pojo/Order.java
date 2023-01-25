package org.malred.service.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单实体类
 */
@Data
@Entity
@Table(name = "order")
public class Order extends BaseEntity {
    private String order_name;
    private String order_description;
    private Double price;
    // 商品id
    private Long good_id;
}
