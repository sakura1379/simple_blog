package com.zlr.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlr.blog.config.RabbitMqConstants;
import com.zlr.blog.dao.mapper.ArticleMapper;
import com.zlr.blog.dao.pojo.Article;
import com.zlr.blog.utils.RabbitMqUtils;
import com.zlr.blog.vo.ArticleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service
 * @Description
 * @create 2022-08-06-上午12:46
 */
@Component
public class ThreadService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private RabbitMqUtils rabbitMqUtils;

    @PostConstruct
    public void initViewCount(){
        //为了保证启动项目的时候，redis中的浏览量 如果redis中没有，则读取数据库的数据，进行初始化
        //便于更新的时候自增
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<>());
        for (Article article : articles) {
            String viewCountStr = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(article.getId()));
            if (viewCountStr == null){
                //初始化
                redisTemplate.opsForHash().put("view_count", String.valueOf(article.getId()),String.valueOf(article.getViewCounts()));
            }
        }
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    //期望此操作在线程池执行 不会影响原有的主线程
    @Async("taskExecutor")
//    @RefreshMysqlMqSender(sender = "ThreadService-updateArticleViewCount")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        int viewCounts = article.getViewCounts();
//        Article articleUpdate = new Article();
//        articleUpdate.setViewCounts(viewCounts +1);
//        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(Article::getId,article.getId());
//        //设置一个 为了在多线程的环境下 线程安全
//        updateWrapper.eq(Article::getViewCounts,viewCounts);
//        // update article set view_count=100 where view_count=99 and id=11
//        articleMapper.update(articleUpdate,updateWrapper);
//        try {
//            Thread.sleep(5000);
//            System.out.println("更新完成了....");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //采用redis进行浏览量的增加
        //hash结构 key 浏览量标识 field 文章id  后面1 表示自增加1
        redisTemplate.opsForHash().increment("view_count",String.valueOf(article.getId()),1);
        //定时任务在ViewCountHandler中

        //还有一种方式是，redis自增之后，直接发送消息到消息队列中，由消息队列进行消费 来同步数据库，比定时任务要好一些
//        String messageId = String.valueOf(UUID.randomUUID());
//        String messageData = "Redis update articleViewCount, refresh mysql data";
//        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        String articleId = String.valueOf(article.getId());
//        Map<String,Object> map=new HashMap<>();
//        map.put("messageId",messageId);
//        map.put("messageData",messageData);
//        map.put("createTime",createTime);
//        map.put("article,articleId);
        ArticleMessage articleMessage = new ArticleMessage();
        articleMessage.setArticleId(article.getId());
        // 发送刷新信息
        rabbitMqUtils.send(RabbitMqConstants.CACHE_QUEUE,articleMessage);
    }

}
