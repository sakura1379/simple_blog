//package com.zlr.blog.utils;
//
//import com.rabbitmq.client.Channel;
//import com.zlr.blog.controller.ArticleController;
//import com.zlr.blog.dao.pojo.Article;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.annotation.Resource;
//import java.io.ByteArrayInputStream;
//import java.io.ObjectInputStream;
//import java.util.Map;
//
///**
// * @author Zenglr
// * @program: simple_blog
// * @packagename: com.zlr.blog.utils
// * @Description
// * @create 2022-09-06-上午10:05
// */
//@Component
//public class MyAckReceiver implements ChannelAwareMessageListener {
//    @Resource
//    ArticleController articleController;
//
//    @Override
//    public void onMessage(Message message, Channel channel) throws Exception {
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        try {
////            byte[] body = message.getBody();
////            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
////            Map<String,String> msgMap = (Map<String,String>) ois.readObject();
////            String messageId = msgMap.get("messageId");
////            String messageData = msgMap.get("messageData");
////            String createTime = msgMap.get("createTime");
////            ois.close();
//
//            if ("mq.cache.info.queue".equals(message.getMessageProperties().getConsumerQueue())){
//                System.out.println("消费的消息来自的队列名为："+message.getMessageProperties().getConsumerQueue());
////                System.out.println("消息成功消费到  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
//                System.out.println("执行mq.cache.info.queue中的消息的业务处理流程......");
//
//
//            }
//
////            if ("fanout.A".equals(message.getMessageProperties().getConsumerQueue())){
////                System.out.println("消费的消息来自的队列名为："+message.getMessageProperties().getConsumerQueue());
////                System.out.println("消息成功消费到  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
////                System.out.println("执行fanout.A中的消息的业务处理流程......");
////
////            }
//
//            channel.basicAck(deliveryTag, true);
////			channel.basicReject(deliveryTag, true);//为true会重新放回队列
//        } catch (Exception e) {
//            channel.basicReject(deliveryTag, false);
//            e.printStackTrace();
//        }
//    }
//
//
//}
//
