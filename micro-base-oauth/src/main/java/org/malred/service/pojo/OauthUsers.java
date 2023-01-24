package org.malred.service.pojo;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限角色实体
 */
@Data
@Entity
@Table(name = "oauth_users")
public class OauthUsers extends BaseEntity {
    private String username;
    private String password;
}
