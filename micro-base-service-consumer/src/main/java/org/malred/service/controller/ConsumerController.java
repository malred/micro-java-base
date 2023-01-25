package org.malred.service.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.malred.service.pojo.Order;
import org.malred.service.service.UserServiceFeignClient;
import org.malred.service.service.fallback.SentinelFallbackClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController extends BaseController<Order> {

    @Override
    public List<Order> findAll() {
        return new ArrayList<Order>();
    }

    @Override
    public Order findById(Long id) {
        return new Order();
    }

    @Override
    public Order insert() {
        return new Order();
    }

    @Override
    public Order update() {
        return new Order();
    }

    @Override
    public void delete(Long id) {
    }

    // 远程调用user服务的客户端
    @Qualifier("org.malred.service.service.UserServiceFeignClient")
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    /**
     * @SentinelResource value：定义资源名
     * blockHandlerClass：指定Sentinel规则异常兜底逻辑所在class类
     * blockHandler：指定Sentinel规则异常兜底逻辑具体哪个⽅法
     * fallbackClass：指定Java运⾏时异常兜底逻辑所在class类
     * ⾃定义兜底逻辑类
     * 注意：兜底类中的⽅法为static静态⽅法
     * fallback：指定Java运⾏时异常兜底逻辑具体哪个⽅法
     */
    @GetMapping("/feign/find/{userId}")
    @SentinelResource(
            value = "findUserById",
            blockHandlerClass = SentinelFallbackClass.class,
            blockHandler = "handleException", fallback = "handleError",
            fallbackClass = SentinelFallbackClass.class
    )
    public String findUserById(@PathVariable Long userId, HttpServletRequest request) {
        // 从路径获取token
        token = request.getQueryString();
        // 缓存token
        // 模拟降级：
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 模拟降级：异常⽐例
//        int i = 1 / 0;
        return userServiceFeignClient.findUserById(userId);
    }
//    @GetMapping("/checkState/{userId}")
//    public String findConsumerOpenState(@PathVariable Long userId, HttpServletRequest request) {
//        // 从路径获取token
//        // 缓存token
//        token = request.getQueryString();
//        return userServiceFeignClient.findDefaultUserState(userId);
//    }
}
