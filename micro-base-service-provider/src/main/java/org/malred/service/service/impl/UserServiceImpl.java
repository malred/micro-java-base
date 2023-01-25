package org.malred.service.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.malred.service.dao.UserDao;
import org.malred.service.pojo.User;
import org.malred.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Cacheable(
            cacheNames = "user",
            // 如果结果为空就不缓存
            unless = "#result==null"
    )
    public User findUserById(Long userId) {
        System.out.println("service接收路由参数: " + userId);
        User user = new User();
        user.setId(userId);
        // 自动类型推断
        Example<? extends User> example = Example.of(user);
        return userDao.findOne(example).get();
    }

    // 添加和更新方法(通过post和put的http请求来区分)
    @Override
    // 事务发起者使用@GlobalTransactional,其他参与者使用@Transactional
    @GlobalTransactional
    @CachePut(
            cacheNames = "user",
            // 将修改结果的id作为缓存的key
            key = "#result.id"
    )
    public User saveUser(User user) {
        userDao.save(user);
        return user; // 如果返回的是修改之后的(修改后会更新缓存),就是更新成功
    }

    // 删除方法
    @Override
    @GlobalTransactional
    @CacheEvict(
            cacheNames = "user"
    )
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
}
