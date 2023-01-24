package org.malred.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置类
 */
@Configuration
@EnableAuthorizationServer //开启认证服务器功能
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 自定义jwt转换器
     */
    @Autowired
    private AccessTokenConvertor accessTokenConvertor;
    private String sign_key = "123456"; //密钥
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;

    /**
     * 用于操作mysql
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 认证服务器最终是以api接⼝的⽅式对外提供服务（校验合法性并⽣成令牌、校验令牌等）
     * 那么，以api接⼝⽅式对外的话，就涉及到接⼝的访问权限，我们需要在这⾥进⾏必要的配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        // 相当于打开endpoints 访问接⼝的开关，这样的话后期我们能够访问该接⼝
        security // 允许客户端表单认证
                .allowFormAuthenticationForClients()
                // 开启端⼝/oauth/token_key的访问权限（允许）
                .tokenKeyAccess("permitAll()")
                // 开启端⼝/oauth/check_token的访问权限（允许）
                .checkTokenAccess("permitAll()");
    }

    /**
     * 客户端详情配置，
     * ⽐如client_id，secret
     * 当前这个服务就如同QQ平台，网站作为客户端需要qq平台进⾏登录授权认证等，
     * 需要提前到QQ平台注册，QQ平台会给网站
     * 颁发client_id等必要参数，表明客户端是谁
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
//        clients.inMemory() // 客户端信息存储在什么地⽅，可以在内存中，可以在数据库⾥
//                .withClient("client_id") // 添加⼀个client配置,指定其client_id
//                // 使用密码编码器,这里也要设置加密,访问时无需加密
//                // http://localhost:9999/oauth/token?client_secret=abcxyz&grant_type=password&username=admin&password=123456&client_id=client_id
//                .secret(passwordEncoder.encode("abcxyz")) // 指定客户端的密码/安全码
//                // 指定客户端所能访问资源id清单，此处的资源id是需要在具体的资源服务器上也配置⼀样
//                // 认证类型/令牌颁发模式，可以配置多个在这⾥，但是不⼀定都⽤，具体使⽤哪种⽅式颁发token，
//                // 需要客户端调⽤的时候传递参数指定
//                .resourceIds("provider")
//                .authorizedGrantTypes("password", "refresh_token") //认证类型
//                .scopes("all"); // 客户端的权限范围，此处配置为all全部即可
        //从数据库中加载客户端详情
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 认证服务器是玩转token的，那么这⾥配置token令牌管理相关
     * （token此时就是⼀个字符串，当下的token需要在服务器端存储，那么存储在哪⾥呢？都是在这⾥配置）
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.tokenStore(tokenStore()) // 指定token存储方法
                .tokenServices(authorizationServerTokenServices()) // token服务的⼀个描述，可以认为是token⽣成细节的描述，⽐如有效时间多少等
                .authenticationManager(authenticationManager) // 指定认证管理器，随后注⼊⼀个到当前类使⽤即可
                .allowedTokenEndpointRequestMethods(
                        // patch更新个别属性,put更新所有
                        HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE
                );
    }

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
        //把自己的转换器注册进来
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConvertor);
        return jwtAccessTokenConverter;
    }

    /*
     生成token store对象(令牌存储对象),token以什么方式存储
     */
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();//内存
        return new JwtTokenStore(jwtAccessTokenConverter());//使用jwt令牌
    }

    /*
     该方法用户获取一个token服务对象(描述了token有效期等信息)
     */
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        //使用默认实现
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);//是否支持令牌刷新
        tokenServices.setTokenStore(tokenStore());
        //针对jwt令牌的添加
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        //设置令牌有效时间（⼀般设置为2个⼩时）
        tokenServices.setAccessTokenValiditySeconds(2 * 60 * 60); // access_token就是我们请求资源需要携带的令牌
        // 设置刷新令牌的有效时间
        tokenServices.setRefreshTokenValiditySeconds(259200);//3天
        return tokenServices;
    }
}
