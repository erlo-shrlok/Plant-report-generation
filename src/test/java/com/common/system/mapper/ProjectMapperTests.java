package com.common.system.mapper;

import com.common.system.entity.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectMapperTests {

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    public void add(){
        Project p = new Project();
        p.setCname("张三");
        p.setAddress("梧桐大街");
        p.setuId(46);
        projectMapper.insert(p);
    }
}
