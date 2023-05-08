package com.common.system.service;

import com.common.system.entity.Project;
import com.common.system.entity.Report;
import com.common.system.util.Result;
import com.github.pagehelper.PageInfo;

public interface ReportService {
    PageInfo<Report> listForPage(Integer pageNum,Integer pageSize,Integer uid);

    Project getProjectByRid(Integer id);

    Report getReportById(Integer id);

    Result insert(Report report);

    Result delete(Integer id);
}
