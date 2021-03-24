package com.hjc.service.impl;

import com.hjc.entity.User;
import com.hjc.mapper.UserMapper;
import com.hjc.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
