package com.zlr.blog.controller;

import com.zlr.blog.service.TagService;
import com.zlr.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.controller
 * @Description
 * @create 2022-08-03-下午3:08
 */
@RestController
@RequestMapping("tags")
@Api(tags = "标签", value = "/tags")
public class TagsController {

    @Autowired
    private TagService tagService;


    @GetMapping("hot")
    @ApiOperation(value = "获取热门标签", notes = "不用传参，设定好为6个")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }

    @GetMapping
    @ApiOperation(value = "获取所有标签")
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    @ApiOperation(value = "获取所有标签细节")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    @ApiOperation(value = "根据标签id获取标签")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
