package com.ccwme.bugmanager.Service.Bug;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccwme.bugmanager.Bean.Bug;
import com.ccwme.bugmanager.Mapper.BugMapper;
import org.springframework.stereotype.Service;

@Service
public class BugService extends ServiceImpl<BugMapper,Bug> implements BugServiceImp {
}
