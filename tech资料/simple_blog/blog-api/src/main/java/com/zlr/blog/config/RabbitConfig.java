package com.zlr.blog.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.config
 * @Description
 * @create 2022-09-06-上午9:24
 */
@Configuration
public class RabbitConfig {
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
                System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
                System.out.println("ConfirmCallback:     " + "原因：" + cause);
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                System.out.println("ReturnCallback:     " + "消息：" + message);
//                System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
//                System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
//                System.out.println("ReturnCallback:     " + "交换机：" + exchange);
//                System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
//            }

            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("ReturnCallback:     " + "消息：" + returnedMessage);
            }
        });

        return rabbitTemplate;
    }

    //创建队列
    @Bean
    public Queue cacheQueue(){
        return new Queue(RabbitMqConstants.CACHE_QUEUE,true);
    }
    //创建交换机：在这里以DirectExchange为例
    @Bean
    public DirectExchange cacheExchange(){
        return new DirectExchange(RabbitMqConstants.CACHE_EXCHANGE,true,false);
    }
    //创建绑定
    @Bean
    public Binding cacheBinding(){
        return BindingBuilder.bind(cacheQueue()).to(cacheExchange()).with(RabbitMqConstants.CACHE_ROUTING_KEY);
    }

    //创建队列
    @Bean
    public Queue refreshRedisQueue(){
        return new Queue(RabbitMqConstants.REFRESH_REDIS_QUEUE,true);
    }
    //创建交换机：在这里以DirectExchange为例
    @Bean
    public DirectExchange refreshRedisExchange(){
        return new DirectExchange(RabbitMqConstants.REFRESH_REDIS_EXCHANGE,true,false);
    }
    //创建绑定
    @Bean
    public Binding refreshRedisBinding(){
        return BindingBuilder.bind(cacheQueue()).to(cacheExchange()).with(RabbitMqConstants.REFRESH_REDIS_ROUTING_KEY);
    }
}
