package com.hjc.service.impl;

import com.hjc.entity.Blog;
import com.hjc.mapper.BlogMapper;
import com.hjc.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kei
 * @since 2021-03-24
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
