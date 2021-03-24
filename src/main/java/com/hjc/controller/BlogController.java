package com.hjc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjc.common.lang.Result;
import com.hjc.entity.Blog;
import com.hjc.service.BlogService;
import com.hjc.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kei
 * @since 2021-03-24
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable("id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog tmp = null;
        if (blog.getId() != null) {
            // 编辑
            tmp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(tmp.getUserId().equals(ShiroUtils.getProfile().getId()), "没有权限编辑");
        } else {
            // 添加
            tmp = new Blog();
            tmp.setUserId(ShiroUtils.getProfile().getId());
            tmp.setCreated(LocalDateTime.now());
            tmp.setStatus(0);
        }
        BeanUtils.copyProperties(blog, tmp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(tmp);
        return Result.success(null);
    }

    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean result = blogService.removeById(id);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }
}
