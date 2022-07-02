package com.packet.gatway.manage.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义全局过滤器，会对所有路由生效
 */
// @Component 让容器扫描到，等同于注册
@Component
public class BlackListFilter implements GlobalFilter, Ordered
{
    
    // 模拟黑名单数据
    private static List<String> blackList = new ArrayList<String>();
    static {
        // 模拟本机地址
        blackList.add("0:0:0:0:0:0:0:1");
    }
    
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        // 获取客户端ip,判断是否在黑名单中，如果在就拒绝访问，不在就放行
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();
        // 从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();
        // 判断客户端ip是否在黑名单中
        if (blackList.contains(clientIp)){
            // 拒绝访问，返回
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("IP:" + clientIp + "在黑名单中，拒绝访问！");
            String date = "Request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(date.getBytes());
            Mono<Void> voidMono = response.writeWith(Mono.just(wrap));
            return voidMono;
        }
        // 合法请求，放行
        return chain.filter(exchange);
        
    }

    /**
     * 返回值表示当前过滤器的顺序（优先级），数值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
