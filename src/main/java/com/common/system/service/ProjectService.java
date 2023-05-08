package com.common.system.service;

import com.common.system.entity.Project;
import com.common.system.util.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProjectService {
    Integer add(Project project);

    PageInfo<Project> listForPage(Integer pageNum,Integer pageSize);

    Result<Project> getById(Integer id);

    Result<Integer> update(Project project);

    Result<Integer> deleteById(Integer id);

    List<Project> getIN(Integer uid);
}
