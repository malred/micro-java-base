package org.malred.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
// 指定带entity注解的实体类所在包,可以扫描到common模块里的实体类
@EntityScan("org.malred.service.pojo")
@EnableDiscoveryClient //开启注册中心客户端(通用注解)
//spirngcloud的Edgware版本开始,不加注解也可以注册
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
