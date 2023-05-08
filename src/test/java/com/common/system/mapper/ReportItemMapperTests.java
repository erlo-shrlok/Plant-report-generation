package com.common.system.mapper;

import com.common.system.entity.ReportItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportItemMapperTests {

    @Autowired
    private ReportItemMapper reportItemMapper;

    @Test
    public void findByRid(){
        System.out.println(reportItemMapper.findByRid(1));
    }

//    @Test
//    public void select(){
//        List<ReportItem> r = reportItemMapper.getByRidAndCid(1,1024);
//        for (ReportItem re: r){
//            System.out.println(re);
//        }
//    }

//    @Test
//    public void getCidsByCids(){
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//    }
}
