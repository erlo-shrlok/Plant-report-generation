package com.common.system.service;

import com.common.system.entity.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceTests {

    @Autowired
    private ReportService reportService;

    @Test
    public void save(){
        Report report = new Report();
        report.setName("111");
        report.setLevel("三级");
        report.setpId(1);
        report.setRemark("测试");
        System.out.println(reportService.insert(report));
    }

    @Test
    public void listForPage(){
        reportService.listForPage(1,5,48);
    }
}
