package com.common.system.service.impl;

import com.common.system.entity.ReportItem;

import com.common.system.mapper.ReportItemMapper;
import com.common.system.service.ReportItemService;
import com.common.system.util.MsgCode;
import com.common.system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class ReportItemServiceImpl implements ReportItemService {

    @Autowired
    private ReportItemMapper reportItemMapper;

    @Override
    public Result<Integer> addItems(List<ReportItem> reportItems) {
        Result<Integer> result = new Result<>();
        for(ReportItem r:reportItems){
            if(r.getId() != null){
                // 如果 ReportItem 有 ID，则执行更新操作
                reportItemMapper.updateAllColumnById(r);
            }else {
                // 如果 ReportItem 没有ID，则执行插入操作
                reportItemMapper.insertAllColumn(r);
            }
        }
        result.setStatus(true);
        result.setCode(MsgCode.SUCCESS);
        result.setMsg("添加成功");
        return result;
    }
}
