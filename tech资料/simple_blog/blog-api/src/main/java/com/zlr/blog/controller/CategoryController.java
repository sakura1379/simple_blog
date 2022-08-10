package com.zlr.blog.controller;

import com.zlr.blog.service.CategoryService;
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
 * @create 2022-08-06-下午4:00
 */
@RestController
@RequestMapping("categorys")
@Api(tags = "分类", value = "/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // /categorys
    @GetMapping
    @ApiOperation(value = "获取所有分类名")
    public Result categories(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    @ApiOperation(value = "获取所有分类细节")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }

    ///category/detail/{id}
    @GetMapping("detail/{id}")
    @ApiOperation(value = "根据分类id获取分类细节")
    public Result categoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }
}