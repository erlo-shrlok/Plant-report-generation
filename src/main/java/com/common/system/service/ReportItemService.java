package com.common.system.service;

import com.common.system.entity.ReportItem;
import com.common.system.util.Result;

import java.util.List;

public interface ReportItemService {
    Result<Integer> addItems(List<ReportItem> reportItems);
}
