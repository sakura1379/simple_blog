package com.zlr.blog.admin.controller;

import com.zlr.blog.admin.model.params.PageParam;
import com.zlr.blog.admin.pojo.Permission;
import com.zlr.blog.admin.service.PermissionService;
import com.zlr.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.controller
 * @Description
 * @create 2022-08-10-下午5:19
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}