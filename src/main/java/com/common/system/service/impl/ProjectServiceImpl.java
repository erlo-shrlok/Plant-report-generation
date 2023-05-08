package com.common.system.service.impl;

import com.common.system.entity.Project;
import com.common.system.mapper.ProjectMapper;
import com.common.system.mapper.RcUserMapper;
import com.common.system.service.ProjectService;
import com.common.system.util.MsgCode;
import com.common.system.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private RcUserMapper rcUserMapper;

    @Override
    public Integer add(Project project) {
        Integer i = projectMapper.insert(project);
        return  i;
    }

    @Override
    public PageInfo<Project> listForPage(Integer pageNum, Integer pageSize) {
        if(pageNum != null&&pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<Project> pList = projectMapper.selectList(null);
        for(Project project:pList){
            // 根据用户id设置用户名
            project.setuName(rcUserMapper.getNameById(project.getuId()));
        }
        return new PageInfo<>(pList);
    }

    @Override
    public Result<Project> getById(Integer id) {
        Result<Project> result = new Result<>();
        Project p = projectMapper.selectById(id);
        if (p!=null){
            result.setData(p);
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        }
        return result;
    }

    @Override
    public Result<Integer> update(Project project) {
        Result<Integer> result = new Result<>();
        Integer i = projectMapper.updateById(project);
        if (i!=0){
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        }
        return result;
    }

    @Override
    public Result<Integer> deleteById(Integer id) {
        Result<Integer> result = new Result<>();
        Integer i = projectMapper.deleteById(id);
        if (i!=0){
            result.setStatus(true);
            result.setCode(MsgCode.SUCCESS);
            result.setMsg("操作成功");
        }
        return result;
    }

    @Override
    public List<Project> getIN(Integer uid) {
        List<Project> projects = projectMapper.getAll(uid);
        // 将不用的信息设置为NULL
        for(Project p : projects){
            p.setuId(null);
            p.setAddress(null);
            p.setCname(null);
            p.setCustomer(null);
            p.setEmail(null);
            p.setPhone(null);
            p.setCreateTime(null);
            p.setIsDeleted(null);
        }
        return projects;
    }
}
