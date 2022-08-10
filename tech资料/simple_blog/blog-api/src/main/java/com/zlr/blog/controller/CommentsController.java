package com.zlr.blog.controller;

import com.zlr.blog.common.aop.LogAnnotation;
import com.zlr.blog.service.CommentsService;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.params.CommentParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.controller
 * @Description
 * @create 2022-08-06-上午12:59
 */
@RestController
@RequestMapping("comments")
@Api(tags = "评论", value = "/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    ///comments/article/{id}

    @GetMapping("article/{id}")
    @ApiOperation(value = "根据文章id获取文章的所有评论")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }


    @PostMapping("create/change")
    @LogAnnotation(module="评论",operator="发表评论")
    @ApiOperation(value = "写评论")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
