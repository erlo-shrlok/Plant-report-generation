package com.common.system.service.impl;

import com.common.system.entity.Project;
import com.common.system.entity.Report;
import com.common.system.mapper.ProjectMapper;
import com.common.system.mapper.ReportItemMapper;
import com.common.system.mapper.ReportMapper;
import com.common.system.service.ReportService;
import com.common.system.util.MsgCode;
import com.common.system.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ReportItemMapper reportItemMapper;

    @Override
    public PageInfo<Report> listForPage(Integer pageNum, Integer pageSize,Integer uid) {
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum,pageSize);
        }
//        List<Report> rList = reportMapper.selectList(null);
        List<Report> rList = reportMapper.findReportByUid(uid);
        return new PageInfo<>(rList);
    }

    @Override
    public Project getProjectByRid(Integer id) {
        Report report = reportMapper.selectById(id);
        Integer pid = report.getpId();
        return projectMapper.selectById(pid);
    }

    @Override
    public Report getReportById(Integer id) {
        return reportMapper.selectById(id);
    }

    @Override
    public Result insert(Report report) {
        Integer row = reportMapper.insert(report);
        return Result.add(row);
    }

    @Override
    public Result delete(Integer id) {
        Result result = new Result();
        // 删除该报告的测评记录
        reportItemMapper.deleteByReportId(id);
        // 删除报告
        Integer row = reportMapper.deleteById(id);
        if(row!=0){
            result.setMsg("删除成功");
            result.setCode(MsgCode.SUCCESS);
            result.setStatus(true);
        }
        return result;
    }
}
