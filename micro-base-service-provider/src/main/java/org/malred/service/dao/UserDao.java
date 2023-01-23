package org.malred.service.dao;


import org.malred.service.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> { }

