package org.malred.service.controller;

import org.malred.service.pojo.User;
import org.malred.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${server.port}")
    private Integer port; //导入yaml文件中的端口号

    // http://bd:8082/user/openstate/1
    @GetMapping("/find/{userId}")
    public User findUserById(@PathVariable Long userId) {
        if (userId == null) {
            return null;
        }
        System.out.println("路由参数: " + userId);
        return userService.findUserById(userId);
//        return port; 测试
    }

    @PostMapping("/save")
    public User insertUser(@RequestBody User user) {
        System.out.println("user -> " + user.toString());
        if (user.getId() != null) {
            // 有id应该是修改
            return null;
        }
        return userService.saveUser(user);
    }

    @PutMapping("/save")
    public User updateUser(@RequestBody User user) {
        System.out.println("user -> " + user.toString());
        if (user.getId() == null) {
            // 没有id应该是添加
            return null;
        }
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{userId}")
    public void saveUser(@PathVariable Long userId) {
        if (userId == null) {
            return;
        }
        userService.deleteUser(userId);
    }
}
