package org.malred.service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局过滤器,对所有路由生效
 */
@Slf4j
@Component //让容器扫描到,相当于注册
public class BlackListFilter implements GlobalFilter, Ordered {
    //模拟黑名单(实际可以去数据库或redis中查询)
    private static List<String> blackList=new ArrayList<>();
    static {
//        blackList.add("0:0:0:0:0:0:0:1"); //模拟本机地址
//        blackList.add("127.0.0.1");
    }
    /**
     * 过滤器核心方法
     * @param exchange 封装了request和response对象的上下文
     * @param chain 网关过滤器链(包含全局过滤器和单路由过滤器)
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //要求: 获取客户端ip,判断是否在黑名单中,在的话就拒绝,不在就放行
        //从上下文中取出request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();
        //拿着ip去黑名单中查询
        if(blackList.contains(clientIp)){
            //拒绝访问,返回
            response.setStatusCode(HttpStatus.UNAUTHORIZED);//状态码
            log.debug("========>ip: "+clientIp+" 在黑名单中,将被拒绝访问!");
            String data="request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));//Mono.just开启数据流,回写数据
        }
        //合法请求,放行
        return chain.filter(exchange);
    }

    /**
     * 返回值表示过滤器顺序(优先级),数值越小,优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
