package com.zlr.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.zlr.blog.common.aop.LogAnnotation;
import com.zlr.blog.common.cache.Cache;
import com.zlr.blog.config.RabbitMqConstants;
import com.zlr.blog.dao.pojo.Article;
import com.zlr.blog.service.ArticleService;
import com.zlr.blog.vo.ArticleMessage;
import com.zlr.blog.vo.ArticleVo;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.params.ArticleParam;
import com.zlr.blog.vo.params.PageParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.controller
 * @Description
 * @create 2022-08-02-下午1:00
 */
@RestController
@RequestMapping("/articles")
@Slf4j
@Api(tags = "文章", value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;



    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping("articleList")
    //加上此注解 代表要对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    @ApiOperation(value = "获取首页文章列表")
    @Cache(expire = 5 * 60 * 1000,name = "Article")
    public Result articles(@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        Result result = articleService.listArticle(pageParams);

        return result;
    }


    /**
     * 首页 最热文章
     * @return
     */
    @GetMapping("hot")
//    @LogAnnotation(module="文章",operator="根据浏览量获取热门文章")
    @ApiOperation(value = "根据浏览量获取热门文章", notes = "不用传参，设定好为5个，只返回id和title")
    @Cache(expire = 5 * 60 * 1000,name = "Article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @GetMapping("new")
//    @LogAnnotation(module="文章",operator="根据时间获取最新文章")
    @ApiOperation(value = "根据时间获取最新文章", notes = "不用传参，设定好为5个，只返回id和title")
    @Cache(expire = 5 * 60 * 1000,name = "Article")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @GetMapping("listArchives")
    @ApiOperation(value = "每一篇文章根据创建时间某年某月发表多少篇文章")
//    @LogAnnotation(module="文章",operator="文章归档 一篇文章根据创建时间某年某月发表多少篇文章")
    @Cache(expire = 5 * 60 * 1000,name = "Article")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 根据文章id获取文章详情
     * @param articleId
     * @return
     */
    @GetMapping("view/{id}")
    @LogAnnotation(module="文章",operator="根据文章id获取文章详情")
    @ApiOperation(value = "根据文章id获取文章详情")
    @Cache(expire = 5 * 60 * 1000,name = "Article")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    @LogAnnotation(module="文章",operator="发表文章")
    @ApiOperation(value = "发表文章")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }

//    /**
//     * 搜索标题，描述，内容
//     * @param keywords
//     * @return
//     */
//    @GetMapping("searchEs")
//    @LogAnnotation(module="文章",operator="根据关键词全文搜索文章（通过es")
//    @ApiOperation(value = "根据关键词全文搜索文章（通过es")
//    public Result search(@RequestParam("keywords") String keywords){
//
//        return articleService.search(keywords);
//    }

    /**
     * 搜索
     * @param articleParam
     * @return
     */
    @GetMapping("search")
    @LogAnnotation(module="文章",operator="根据标题相似搜索文章（通过mysql")
    @ApiOperation(value = "根据标题相似搜索文章（通过mysql")
    public Result search(@RequestBody ArticleParam articleParam){
        String search = articleParam.getSearch();
        return articleService.searchArticle(search);
    }

//    /**
//     * es更新文章内容
//     * @return
//     */
//    @GetMapping("search")
//    @LogAnnotation(module="文章",operator="更新es的文章")
//    @ApiOperation(value = "更新es的文章")
//    public Result refresh(){
//        return articleService.refreshEs();
//    }

    /**
     * redis更新后发送消息给到mysql更新浏览量
     * @param articleMessage
     * @return
     */
//    @LogAnnotation(module="文章",operator="redis更新后发送消息给到mysql更新浏览量")
    @RabbitListener(queues= RabbitMqConstants.CACHE_QUEUE)
    public void refresh(ArticleMessage articleMessage, @Headers Map<String,Object> headers){
        for(Map.Entry<String, Object> map :headers.entrySet()){
            log.info("headers ===> " + map.getKey() + "=" + map.getValue());
        }
        Result result = articleService.refreshMysqlView(articleMessage.getArticleId());
        log.info(result.toString());

    }

//    /**
//     * redis更新后发送消息给到mysql更新浏览量
//     * @param message
//     * @return
//     */
//    @LogAnnotation(module="文章",operator="redis更新后发送消息给到mysql更新浏览量")
//    @RabbitListener(queues= RabbitMqConstants.CACHE_QUEUE)
//    public Result refresh(Message message, Channel channel){
//        log.info("redis更新后发送消息给到mysql更新浏览量");
//        try {
//            //手动确认消息已经被消费
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            byte[] body = message.getBody();
//            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
//            ArticleMessage articleMessage = (ArticleMessage) ois.readObject();
//            Result result = articleService.refreshMysqlView(articleMessage.getArticleId());
//            log.info(message.toString());
//            return result;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return Result.fail(-999, e.getMessage());
//        }
//    }

    @RabbitListener(queues= RabbitMqConstants.REFRESH_REDIS_QUEUE)
    public void refreshRedis(String area, @Headers Map<String,Object> headers){
        for(Map.Entry<String, Object> map :headers.entrySet()){
            log.info("headers ===> " + map.getKey() + "=" + map.getValue());
        }
        Result result = articleService.deleteRedisCache(area);
        log.info(result.toString());

    }
}
