package org.malred.service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.malred.service.dao.UserDao;
import org.malred.service.pojo.User;
import org.malred.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findDefaultUserByUserId(Long userId) {
        System.out.println("service接收路由参数: " + userId);
        User user = new User();
        user.setId(userId);
        //查询默认简历
//        user.setIsDefault(1);
        // 自动类型推断
        Example<? extends User> example = Example.of(user);
        return userDao.findOne(example).get();
    }
}
