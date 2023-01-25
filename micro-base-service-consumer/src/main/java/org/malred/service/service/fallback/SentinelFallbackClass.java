package org.malred.service.service.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * sentinel自定义兜底类
 */
public class SentinelFallbackClass {
    // 整体要求和当时Hystrix⼀样，这⾥还需要在形参最后添加BlockException参数，⽤于接收异常
    // 注意：⽅法是静态的
    /*
        注意
    　　1.兜底方法的参数要和被的兜底方法的一样
    　　2.返回值要和被兜底方法的返回值一样
    　　3.限流兜底方法的   BlockException blockException 必须要有，
         异常兜底的  Throwable blockException 参数好像不是必须，但是没写他也没找到异常兜底。
    　　4.方法必须是public static 修饰。
     */
    public static String handleException(Long userId, BlockException blockException) {
        return "error!!!";
    }
    public static String handleError(Long userId, Throwable blockException) {
        return "error!!!";
    }
}
