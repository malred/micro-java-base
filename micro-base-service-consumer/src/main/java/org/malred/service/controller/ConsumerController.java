package org.malred.service.controller;

import org.malred.service.service.UserServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;
    @GetMapping("/checkState/{userId}")
    public String findConsumerOpenState(@PathVariable Long userId) {
        return userServiceFeignClient.findDefaultUserState(userId);
    }
}
