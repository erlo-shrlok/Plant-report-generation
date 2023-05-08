package com.common.system.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriterionMapperTests {

    @Autowired
    private CriterionMapper criterionMapper;

    @Test
    public void getClaList(){
        System.out.println(criterionMapper.getClaList());
    }

    @Test
    public void getAll(){
        System.out.println(criterionMapper.getAll());
    }
//    @Test
//    public void getTypeListByCla(){
//        System.out.println(criterionMapper.getTypeListByCla("安全物理环境"));
//    }
//
//    @Test
//    public void getReferByType(){
//        System.out.println(criterionMapper.getReferByType("防雷击"));;
//    }

//    @Test
//    public void getCidsByType(){
//        System.out.println(criterionMapper.getCidsByType("防雷击"));
//    }
}
