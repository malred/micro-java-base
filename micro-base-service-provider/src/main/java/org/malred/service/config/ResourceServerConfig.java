package org.malred.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 资源服务配置类
 */
@Configuration
@EnableWebSecurity // 开启web访问安全
@EnableResourceServer //开启资源服务器功能
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private String sign_key = "123456"; // jwt签名密钥

    /**
     * 返回jwt令牌转换器（帮助我们⽣成jwt令牌的）
     * 在这⾥，我们可以把签名密钥传递进去给转换器对象
     *
     * @return
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(sign_key); //签名密钥
        jwtAccessTokenConverter.setVerifier(new MacSigner(sign_key)); //校验用的密钥,和签名密钥一致
        return jwtAccessTokenConverter;
    }

    /*
     生成token store对象(令牌存储对象),token以什么方式存储
     */
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());//使用jwt令牌
    }

    /**
     * 该⽅法⽤于定义资源服务器向远程认证服务器发起请求，进⾏token校验等事宜
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //jwt令牌改造
        resources.resourceId("provider").tokenStore(tokenStore()).stateless(true); // 无状态设置
    }

    /**
     * 场景：⼀个服务中可能有很多资源（API接⼝）
     * 某⼀些API接⼝，需要先认证，才能访问
     * 某⼀些API接⼝，压根就不需要认证，本来就是对外开放的接⼝
     * 我们就需要对不同特点的接⼝区分对待（在当前configure⽅法中完成），设置是否需要经过认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    // 设置session的创建策略（根据需要创建即可）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                // user为前缀的请求需要认证
                // feign远程调用需要认证,可以查询的不需要认证,其他的需要,
                // 远程调用的客户端需要认证,服务端(被远程调用的不能让其他的访问,
                .antMatchers("/user/**").authenticated()
                .anyRequest().permitAll(); // 其他请求不认证
    }
}

