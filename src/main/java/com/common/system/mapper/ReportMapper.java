package com.common.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.entity.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportMapper extends BaseMapper<Report> {
    List<Report> findReportByUid(Integer uid);
}
