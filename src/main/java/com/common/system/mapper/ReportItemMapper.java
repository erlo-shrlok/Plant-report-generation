package com.common.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.system.entity.Refer;
import com.common.system.entity.ReportItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportItemMapper extends BaseMapper<ReportItem> {
    List<ReportItem> findByRid(Integer rid);

    Integer deleteByReportId(Integer id);
//    List<ReportItem> getByRidAndCid(Integer rId,Integer cId);

//    List<Refer> getReportItemByCids(List<Integer> cids);
}
