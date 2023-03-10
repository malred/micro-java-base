package org.malred.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 认证服务器安全配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private org.malred.service.service.jdbcUserDetailService jdbcUserDetailService;

    /**
     * 注册一个认证管理类对象到容器
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    /**
     * 密码编码对象（密码不进⾏加密处理）
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 数据库的数据是加密过的数据
        return new BCryptPasswordEncoder();
    }

    /**
     * 处理⽤户名和密码验证事宜
     * 1）客户端传递username和password参数到认证服务器
     * 2）⼀般来说，username和password会存储在数据库中的⽤户表中
     * 3）根据⽤户表中数据，验证当前传递过来的⽤户信息的合法性
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在这个⽅法中就可以去关联数据库了，当前我们先把⽤户信息配置在内存中
        // 实例化⼀个⽤户对象(相当于数据表中的⼀条⽤户记录)
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(passwordEncoder.encode("123456"))
//                .roles("ADMIN");
        auth.userDetailsService(jdbcUserDetailService).passwordEncoder(passwordEncoder);
    }
}
