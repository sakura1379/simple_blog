//package com.zlr.blog.common.mq;
//
//import com.zlr.blog.config.RabbitMqConstants;
//import com.zlr.blog.utils.RabbitMqUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @author Zenglr
// * @program: simple_blog
// * @packagename: com.zlr.blog.common.mq
// * @Description
// * @create 2022-09-06-下午2:45
// */
//@Aspect
//@Component
//public class RefreshMysqlMqAspect {
//
//    @Resource
//    private RabbitMqUtils rabbitMqUtils;
//
//    @Pointcut("@annotation(com.zlr.blog.common.mq.RefreshMysqlMqSender)")
//    public void pointCut() {
//
//    }
//
//    @Around("pointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        //执行方法
//        Object result = point.proceed();
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//        RefreshMysqlMqSender senderAnnotation = method.getAnnotation(RefreshMysqlMqSender.class);
//        String messageId = String.valueOf(UUID.randomUUID());
//        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Map<String,Object> map=new HashMap<>();
//        map.put("messageId",messageId);
//        map.put("messageData",senderAnnotation.msg());
//        map.put("createTime",createTime);
//        // 发送刷新信息
//        rabbitMqUtils.send(RabbitMqConstants.CACHE_QUEUE,map);
//        return result;
//    }
//}
