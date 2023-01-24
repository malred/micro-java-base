package org.malred.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.malred.service.service.UserServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    // 缓存token
    public static String token;
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @GetMapping("/checkState/{userId}")
    public String findConsumerOpenState(@PathVariable Long userId, HttpServletRequest request) {
        // 从路径获取token
        token = request.getQueryString();
        // 缓存token
        return userServiceFeignClient.findDefaultUserState(userId);
    }
}
