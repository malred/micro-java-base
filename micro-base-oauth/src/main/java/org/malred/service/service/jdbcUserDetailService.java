package org.malred.service.service;

import org.malred.service.dao.UserRepository;
import org.malred.service.pojo.OauthUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 根据⽤户名从数据库加载⽤户信息
 */
@Service
public class jdbcUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    /**
     * 根据username查询出该⽤户的所有信息，封装成UserDetails类型的对象返回，⾄于密码，框架会⾃动匹配
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OauthUsers users = userRepository.findByUsername(username);
        return new User(users.getUsername(),users.getPassword(),new ArrayList<>());
    }
}
