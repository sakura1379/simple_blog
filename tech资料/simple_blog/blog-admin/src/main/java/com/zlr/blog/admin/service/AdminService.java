package com.zlr.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlr.blog.admin.mapper.AdminMapper;
import com.zlr.blog.admin.pojo.Admin;
import com.zlr.blog.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.service
 * @Description
 * @create 2022-08-09-下午11:49
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    public List<Permission> findPermissionByAdminId(Long adminId) {
        //SELECT * FROM `ms_permission` where id in (select permission_id from ms_admin_permission where admin_id=1)
        return adminMapper.findPermissionByAdminId(adminId);
    }
}