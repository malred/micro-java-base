package org.malred.service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;

/**
 * feign拦截器
 */
@Data
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // "?access_token=" +
        // 远程调用前添加token
        template.uri(template.url()+"?"+getToken());
    }

    public String getToken() {
        return Token.token;
    }
}
