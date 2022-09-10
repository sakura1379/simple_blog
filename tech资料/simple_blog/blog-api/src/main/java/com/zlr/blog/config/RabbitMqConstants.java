package com.zlr.blog.config;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.config
 * @Description
 * @create 2022-08-20-下午2:54
 */
@Data
public class RabbitMqConstants {
    //队列名词
    public static final String CACHE_QUEUE = "mq.cache.info.queue";
    //交换机名词
    public static final String CACHE_EXCHANGE = "mq.cache.info.exchange";
    //路由规则,实际为字符串
    public static final String CACHE_ROUTING_KEY = "mq.cache.info.routing.key";

    //队列名词
    public static final String REFRESH_REDIS_QUEUE = "mq.redis.info.queue";
    //交换机名词
    public static final String REFRESH_REDIS_EXCHANGE = "mq.redis.info.exchange";
    //路由规则,实际为字符串
    public static final String REFRESH_REDIS_ROUTING_KEY = "mq.redis.info.routing.key";
}
