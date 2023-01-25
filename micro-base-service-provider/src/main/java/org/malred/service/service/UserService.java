package org.malred.service.service;

import org.malred.service.pojo.User;
public interface UserService {
    User findUserById(Long userId);
    User saveUser(User user);
    void deleteUser(Long userId);
}
