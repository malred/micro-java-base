package org.malred.service.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 资源服务器也需要定义转换器,把载荷信息设置到认证对象的details属性中
 */
@Component
public class AccessTokenConvertor extends DefaultAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        oAuth2Authentication.setDetails(map); //将map放入认证对象中,认证对象在controller中可以拿到
        return oAuth2Authentication;
    }
}
