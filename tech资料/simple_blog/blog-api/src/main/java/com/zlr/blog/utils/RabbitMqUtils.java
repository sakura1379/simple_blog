package com.zlr.blog.utils;

import com.zlr.blog.vo.ArticleMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.utils
 * @Description
 * @create 2022-09-06-下午2:47
 */
@Component
public class RabbitMqUtils {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送到指定Queue
     * @param queueName
     * @param articleMessage
     */
    public void send(String queueName, ArticleMessage articleMessage){
        this.rabbitTemplate.convertAndSend(queueName, articleMessage);
    }
}
