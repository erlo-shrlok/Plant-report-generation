package com.common.system.controller;

import com.common.system.entity.Criterion;
import com.common.system.service.CriterionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/criterion")
public class CriterionController {

    @Autowired
    private CriterionService criterionService;

    /**
     * 指标页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView modelAndView){
        List<Criterion> criteria = criterionService.allCriterion();
        modelAndView.setViewName("/system/admin/criterion/list");
        modelAndView.addObject("criterion",criteria);
        return modelAndView;
    }

    /**
     * 获取安全大类列表
     * @return
     */
    @ResponseBody
    @RequestMapping("getClaList")
    public List<String> getClaList(){
        return criterionService.getClaList();
    }

    /**
     * 查询某安全大类下的安全子类
     * @param cla
     * @return
     */
    @ResponseBody
    @RequestMapping("getTypeListByCla")
    public List<String> getTypeListByCla(@RequestParam("cla")String cla){
        return criterionService.getTypeListByCla(cla);
    }

    /**
     * 导入指标
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadExcel")
    public String uploadExcel(MultipartFile file){
        criterionService.uploadExcel(file);
        return "success";
    }
    /**
     * 导入指标2
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadExcel2")
    public String uploadExcel2(MultipartFile file){
        criterionService.uploadExcel2(file);
        return "success";
    }

    /**
     * 编制报告
     * @param modelAndView
     * @return
     */
    @GetMapping("/all/{rId}")
    public ModelAndView getAll(@PathVariable String rId,ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/report/criteria");
        List<Criterion> criteria = criterionService.findAll(Integer.valueOf(rId));
        modelAndView.addObject("criteria",criteria);
        modelAndView.addObject("rId",rId);
        return modelAndView;
    }

}
