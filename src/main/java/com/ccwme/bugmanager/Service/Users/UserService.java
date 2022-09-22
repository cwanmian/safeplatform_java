package com.ccwme.bugmanager.Service.Users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccwme.bugmanager.Bean.Users;
import com.ccwme.bugmanager.Mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, Users> implements UserServiceImp {
}
