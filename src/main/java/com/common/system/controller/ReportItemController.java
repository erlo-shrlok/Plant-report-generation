package com.common.system.controller;

import com.common.system.entity.ReportItem;
import com.common.system.service.ReportItemService;
import com.common.system.util.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reportItem")
public class ReportItemController {

    @Autowired
    private ReportItemService reportItemService;

    /**
     * 批量新增测评项
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("addReportItems")
    public Result addReportItems(@RequestBody List<ReportItem> reportItemsJson) throws IOException {
        return reportItemService.addItems(reportItemsJson);
    }
}
