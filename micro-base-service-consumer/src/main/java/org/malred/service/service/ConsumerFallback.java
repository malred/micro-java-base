package org.malred.service.service;

import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;

/**
 * 降级回退逻辑需要定义⼀个类，实现FeignClient接⼝，实现接⼝中的⽅法
 */
@Component  //别忘了这个注解，还应该被扫描到
public class ConsumerFallback implements UserServiceFeignClient{
    @Override
    public String findUserById(Long userId ) {
        return "-1";
    }
}
