package org.malred.service.dao;

import org.malred.service.pojo.OauthUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<OauthUsers,Long> {
    OauthUsers findByUsername(String username);
}
