package com.zlr.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zlr.blog.dao.pojo.Article;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.params.ArticleParam;
import com.zlr.blog.vo.params.PageParams;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service
 * @Description
 * @create 2022-08-02-下午1:13
 */
public interface ArticleService extends IService<Article> {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);

    /**
     * 文章搜索
     * @param search
     * @return
     */
    Result searchArticle(String search);
//
//    /**
//     * 根据关键词从es中搜索
//     * @param keywords
//     * @return
//     */
//    Result search(String keywords);
//
//    /**
//     * es更新文章内容
//     * @return
//     */
//    Result refreshEs();

    /**
     * mysql更新浏览量
     * @return
     */
    Result refreshMysqlView(Long articleId);

    /**
     * 删除指定前缀的缓存
     * @param prefix
     * @return
     */
    Result deleteRedisCache(String prefix);
}
