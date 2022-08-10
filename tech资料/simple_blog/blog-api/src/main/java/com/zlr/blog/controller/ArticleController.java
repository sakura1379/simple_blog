package com.zlr.blog.controller;

import com.zlr.blog.common.aop.LogAnnotation;
import com.zlr.blog.service.ArticleService;
import com.zlr.blog.vo.ArticleVo;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.params.ArticleParam;
import com.zlr.blog.vo.params.PageParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "根据浏览量获取热门文章", notes = "不用传参，设定好为5个，只返回id和title")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @GetMapping("new")
    @ApiOperation(value = "根据时间获取最新文章", notes = "不用传参，设定好为5个，只返回id和title")
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
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    @LogAnnotation(module="文章",operator="发表文章")
    @ApiOperation(value = "发表文章")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }

}
