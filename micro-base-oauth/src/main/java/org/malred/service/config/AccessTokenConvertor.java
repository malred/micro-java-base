package org.malred.service.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * JWT拓展--⽣成JWT令牌时存⼊扩展信息(不要放敏感信息)（⽐如clientIp）
 */
@Component
public class AccessTokenConvertor extends DefaultAccessTokenConverter {
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        //获取到request对象
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        //获取客户端ip(如果客户端经过代理之后到达当前服务,
        //则此处获取的不是真实浏览器客户端ip,则需要从请求头里获取真实ip)
        String remoteAddr = request.getRemoteAddr();
        Map<String,String> stringMap = (Map<String, String>) super.convertAccessToken(token, authentication);
        stringMap.put("clientIp",remoteAddr);
        return stringMap;
    }
}
