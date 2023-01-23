package org.malred.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.malred.service.pojo.User;
import org.malred.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${server.port}")
    private Integer port; //导入yaml文件中的端口号
    // http://bd:8082/user/openstate/1
    @GetMapping("/openstate/{userId}")
    public String findDefaultResumeState(@PathVariable Long userId) {
        System.out.println("路由参数: "+userId);
        log.info(userService.findDefaultUserByUserId(userId).toString());
        return userService.findDefaultUserByUserId(userId).getName();
//        return port; 测试
    }
}
