package org.malred.service.service;

import org.springframework.stereotype.Component;

/**
 * 降级回退逻辑需要定义⼀个类，实现FeignClient接⼝，实现接⼝中的⽅法
 */
@Component  //别忘了这个注解，还应该被扫描到
public class ConsumerFallback implements UserServiceFeignClient{
    @Override
    public String findDefaultUserState(Long userId) {
        return "-1";
    }
}
