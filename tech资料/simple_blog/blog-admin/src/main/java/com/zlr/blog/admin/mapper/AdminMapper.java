package com.zlr.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlr.blog.admin.pojo.Admin;
import com.zlr.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.mapper
 * @Description
 * @create 2022-08-09-下午11:12
 */
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT * FROM zlr_permission where id in (select permission_id from zlr_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);
}
