package com.common.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper extends BaseMapper<Project> {

    List<Project> getAll(Integer uid);
}
