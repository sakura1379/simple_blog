package com.zlr.blog.vo.params;

import com.zlr.blog.vo.CategoryVo;
import com.zlr.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.vo.params
 * @Description
 * @create 2022-08-06-下午10:04
 */
@Data
public class ArticleParam {
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

    private String search;
}
