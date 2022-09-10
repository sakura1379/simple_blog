package com.zlr.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zlr.blog.config.RabbitMqConstants;
import com.zlr.blog.dao.dos.Archives;
import com.zlr.blog.dao.mapper.ArticleBodyMapper;
import com.zlr.blog.dao.mapper.ArticleMapper;
import com.zlr.blog.dao.mapper.ArticleTagMapper;
import com.zlr.blog.dao.pojo.Article;
import com.zlr.blog.dao.pojo.ArticleBody;
import com.zlr.blog.dao.pojo.ArticleTag;
import com.zlr.blog.dao.pojo.SysUser;
import com.zlr.blog.service.*;
import com.zlr.blog.utils.RabbitMqUtils;
import com.zlr.blog.utils.SensitiveFilter;
import com.zlr.blog.utils.UserThreadLocal;
import com.zlr.blog.vo.*;
import com.zlr.blog.vo.params.ArticleParam;
import com.zlr.blog.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service.impl
 * @Description
 * @create 2022-08-02-下午1:13
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>  implements ArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Resource
    SysUserService sysUserService;

    @Resource
    TagService tagService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ThreadService threadService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private AmqpTemplate rabbitTemplate;

    @Resource
    private SensitiveFilter sensitiveFilter;

//    @Resource
//    private ArticleRepository articleRepository;

//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 1、分页查询article数据库表
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>(); //查询条件
//        //是否置顶进行排序,        //时间倒序进行排列相当于order by create_data desc
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        //分页查询用法 https://blog.csdn.net/weixin_41010294/article/details/105726879
//        List<Article> records = articlePage.getRecords();
//        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
//        List<ArticleVo> articleVoList =copyList(records,true,true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());

        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        log.info("recods======"+records);
        for (Article record : records) {
            String viewCount = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(record.getId()));
            if (viewCount != null){
                log.info("viewCount===="+viewCount);
                record.setViewCounts(Integer.parseInt(viewCount));
            }
        }
        return Result.success(copyList(records,true,true));
    }


    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

//    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
//        ArticleVo articleVo = new ArticleVo();
//        //BeanUtils.copyProperties用法   https://blog.csdn.net/Mr_linjw/article/details/50236279
//        BeanUtils.copyProperties(article, articleVo);
//        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
//        //并不是所有的接口都需要标签和作者信息
//        if(isTag){
//            Long articleId = article.getId();
//            articleVo.setTags(tagService.findTagsByArticleId(articleId));
//        }
//        if (isAuthor) {
//            //拿到作者id
//            Long authorId = article.getAuthorId();
//
//            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
//        }
//        return articleVo;
//    }




    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory){
        log.info("article===="+article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口 都需要标签 ，作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId().toString());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        log.info("articlevo===="+articleVo);
        return articleVo;
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        log.info("hotArticle======" + articles.toString());
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by create_date desc desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        log.info("newArticles======" + articles.toString());
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        log.info("listArchives======" + archivesList.toString());
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据bodyId和categoryid 去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);
        //查看完文章了，新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        // 更新 增加了此次接口的 耗时 如果一旦更新出问题，不能影响 查看文章的操作
        //线程池  可以把更新操作 扔到线程池中去执行，和主线程就不相关了
        threadService.updateArticleViewCount(articleMapper,article);

        String viewCount = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(articleId));
        if (viewCount != null){
            articleVo.setViewCounts(Integer.parseInt(viewCount));
        }
        return Result.success(articleVo);
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        //此接口 要加入到登录拦截当中
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. 发布文章 目的 构建Article对象
         * 2. 作者id  当前的登录用户
         * 3. 标签  要将标签加入到 关联列表当中
         * 4. body 内容存储 article bodyId
         */
        Article article = new Article();
        boolean isEdit = false;
        if (articleParam.getId() != null){
            article = new Article();
            article.setId(articleParam.getId());
            article.setTitle(sensitiveFilter.filter(articleParam.getTitle()));
            article.setSummary(sensitiveFilter.filter(articleParam.getSummary()));
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            articleMapper.updateById(article);
            isEdit = true;
        }else{
            article = new Article();
            article.setAuthorId(sysUser.getId());
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(sensitiveFilter.filter(articleParam.getTitle()));
            article.setSummary(sensitiveFilter.filter(articleParam.getSummary()));
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            //插入之后 会生成一个文章id
            this.articleMapper.insert(article);
        }
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                if (isEdit){
                    //先删除
                    LambdaQueryWrapper<ArticleTag> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(ArticleTag::getArticleId,articleId);
                    articleTagMapper.delete(queryWrapper);
                }
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        if (isEdit){
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(sensitiveFilter.filter(articleParam.getBody().getContent()));
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            LambdaUpdateWrapper<ArticleBody> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(ArticleBody::getArticleId,article.getId());
            articleBodyMapper.update(articleBody, updateWrapper);
        }else {
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(sensitiveFilter.filter(articleParam.getBody().getContent()));
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            articleBodyMapper.insert(articleBody);

            article.setBodyId(articleBody.getId());
            articleMapper.updateById(article);
        }
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());

        if (isEdit){
            //发送一条消息给mq 当前文章更新了，更新一下缓存吧

//            ArticleMessage articleMessage = new ArticleMessage();
//            articleMessage.setArticleId(article.getId());
            //是否应该删除所有article有关的缓存？毕竟hot文章之类的可能有它
            rabbitTemplate.convertAndSend(RabbitMqConstants.REFRESH_REDIS_QUEUE, "Article");
//            rocketMQTemplate.convertAndSend("blog-update-article",articleMessage);
        }
        return Result.success(map);
    }

    @Override
    public Result searchArticle(String search) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.like(Article::getTitle,search);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

//    @Override
//    public Result search(String keywords){
//        // 对所有索引进行搜索
//        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keywords);
//
//        Iterable<Article> listIt =  articleRepository.search(queryBuilder);
//
//        //Iterable转list
//        List<Article> articleList= Lists.newArrayList(listIt);
//
//        return Result.success(articleList);
//    }
//
//    @Override
//    public Result refreshEs(){
//        articleRepository.deleteAll();
//        List<Article> list = list();
//        articleRepository.saveAll(list);
//        log.info(list.toString());
//        return Result.success(list);
//    }

    @Override
    public Result refreshMysqlView(Long articleId){
        String viewCount = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(articleId));
        log.info("refresh mysql viewCount====" + viewCount);
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(Integer.parseInt(viewCount));
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,articleId);
        articleMapper.update(articleUpdate,updateWrapper);
        Map<String,String> map = new HashMap<>();
        map.put("id",articleId.toString());
        return Result.success(map);
    }

    @Override
    public Result deleteRedisCache(String prefix){
        log.info("deleteRedisCache, prefix=====" + prefix);
        Set<String> keys = redisTemplate.keys(prefix + "*");
        Long deleteNum = redisTemplate.delete(keys);
        return Result.success(deleteNum);
    }
}
