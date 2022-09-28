package com.ccwme.bugmanager.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccwme.bugmanager.Bean.Bug;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BugMapper extends BaseMapper<Bug> {
}
