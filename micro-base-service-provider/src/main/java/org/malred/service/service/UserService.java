package org.malred.service.service;

import org.malred.service.pojo.User;
public interface UserService {
    User findDefaultUserByUserId(Long userId);
}
