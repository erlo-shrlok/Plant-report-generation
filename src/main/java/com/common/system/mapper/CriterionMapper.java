package com.common.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.entity.Criterion;
import com.common.system.entity.Refer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CriterionMapper extends BaseMapper<Criterion> {
    List<String> getClaList();

    List<String> getTypeListByCla(String cla);

    List<Refer> getReferByType(Map<String, Object> params);

    List<Criterion> getAll();

    void insert2(Criterion criterion);

    void deleteAll(Object o);
}
