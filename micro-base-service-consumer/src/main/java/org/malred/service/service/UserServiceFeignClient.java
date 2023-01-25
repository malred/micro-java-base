package org.malred.service.service;

import org.malred.service.config.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

//如果有这个给类限定的url映射,在回退的时候会报错
//使⽤fallback的时候，类上的@RequestMapping的url前缀限定，改成配置在@FeignClient的path属性中
//@RequestMapping("/resume") //feign支持springmvc接口
//表明当前类是一个Feign客户端
//value指定该客户端要请求的服务名称(注册中心上的服务提供者的服务名称)
//@FeignClient(value = "micro-base-provider",
//        fallback = ConsumerFallback.class,
//        // 远程调用的接口
//        path = "/user")
@FeignClient(value = "micro-base-provider",
//        fallback = ConsumerFallback.class,
        configuration = FeignInterceptor.class,
        path = "/user")
// http://bd:8083/consumer/checkState/1
public interface UserServiceFeignClient {
    //feign->拼装url,发起请求
    //调用该本地方法,实际是远程请求调用
    @RequestMapping("/find/{userId}")
    public String findUserById(@PathVariable("userId") Long userId );
}
