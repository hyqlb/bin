package com.packet.gatway.manage.predicates;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 自定义断言
 * 需要声明是Springboot的Bean，添加注解@Component，名称必须以RoutePredicateFactory结尾，这个是命名约束。
 * 继承父类AbstractRoutePredicateFactory，并重写方法。
 * 需要定义一个Config静态内部类，来接收断言配置的数据。
 * 在重写的shortcutFieldOrder方法中，绑定Config中的属性。传入数组的内容需要与Config中的属性一致。
 * 在重写的apply方法中，实现具体验证逻辑。
 */
@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenConfig>
{

    public TokenRoutePredicateFactory() {
        super(TokenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Consumer consumer) {
        return null;
    }

    @Override
    public Predicate<ServerWebExchange> apply(TokenConfig config) {
        return exchange ->{
            // 获取request请求参数
            MultiValueMap<String, String> valueMap = exchange.getRequest().getQueryParams();
            boolean flag = false;
            List<String> list = new ArrayList<String>();
            // 将request请求的value保存到集合中
            valueMap.forEach((k, v)->{
                list.addAll(v);
            });
            // 判断有没有和匹配文件中的token相同的值
            for (String s:list){
                System.out.println("Token:" + s);
                if (StringUtils.equalsIgnoreCase(s, config.getToken())){
                    flag = true;
                    break;
                }
            }
            return flag;
        };
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public ShortcutType shortcutType() {
        return null;
    }

    /**
     * 用来吧配置中的值变成一个集合
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("token");
    }

    @Override
    public String shortcutFieldPrefix() {
        return null;
    }
}
