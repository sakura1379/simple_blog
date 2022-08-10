package com.zlr.blog.admin.service;

import com.zlr.blog.admin.pojo.Admin;
import com.zlr.blog.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.service
 * @Description
 * @create 2022-08-10-上午12:00
 */
@Service
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        //权限认证
        //请求路径
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null){
            return  false;
        }
        if (1 == admin.getId()){
            //超级管理员
            return true;
        }
        Long id = admin.getId();
        List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
        requestURI = StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }
}

