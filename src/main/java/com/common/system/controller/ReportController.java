package com.common.system.controller;

import com.common.system.entity.Cla;
import com.common.system.entity.Project;
import com.common.system.entity.Report;
import com.common.system.service.CriterionService;
import com.common.system.service.ReportService;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.PageBean;
import com.common.system.util.Result;
import com.common.system.util.WordUtil;
import com.common.system.vo.ReportVO;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报告
 */
@Controller
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

//    @Autowired
//    private ReportItemService reportItemService;

    @Autowired
    private CriterionService criterionService;

    private ReportVO reportVO = null;

    @ResponseBody
    @RequestMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        return reportService.delete(id);
    }

    /**
     * 列表页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/report/list");
        return modelAndView;
    }

    /**
     * 添加报告页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/report/add");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(Report report){
        return reportService.insert(report);
    }

    /**
     * 分页展示报告-显示当前用户的所有报告
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "page")
    public PageBean<Report> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start,
                                         @RequestParam(value = "length", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "date", required = false) String date,
                                         @RequestParam(value = "search", required = false) String search,
                                         HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        // 获取当前用户的id
        Subject subject = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser)subject.getPrincipal();
        Integer uid = user.getId();
        PageInfo<Report> pageInfo = reportService.listForPage((start / pageSize) + 1, pageSize,uid);
        return new PageBean<Report>(pageInfo);
    }

    /**
     * 返回创建报告页面并装配数据--废接口
     * @param id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "createReport2/{id}",method = RequestMethod.GET)
    public ModelAndView createReport(@PathVariable Integer id,ModelAndView modelAndView){
        Report report = reportService.getReportById(id);
        Project project = reportService.getProjectByRid(id);
        reportVO = new ReportVO();
        reportVO.setCreateTime(project.getCreateTime());
        reportVO.setrId(id);
        reportVO.setAddress(project.getAddress());
        reportVO.setCname(project.getCname());
        reportVO.setCustomer(project.getCustomer());
        reportVO.setName(report.getName()+"电力安全测评报告");
        reportVO.setEmail(project.getEmail());
        reportVO.setLevel(report.getLevel());
        reportVO.setPhone(project.getPhone());
        reportVO.setSystem(report.getName());
        modelAndView.addObject("bean",reportVO);
        modelAndView.setViewName("/system/admin/report/createReport");
        return modelAndView;
    }

    /**
     * 生成报告
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "createReport/{id}")
    public
    @ResponseBody
    ResponseEntity<byte[]> create(@PathVariable Integer id) throws Exception {

        Report report = reportService.getReportById(id);
        Project project = reportService.getProjectByRid(id);
        reportVO = new ReportVO();
        reportVO.setCreateTime(project.getCreateTime());
        reportVO.setrId(id);
        reportVO.setAddress(project.getAddress());
        reportVO.setCname(project.getCname());
        reportVO.setCustomer(project.getCustomer());
        reportVO.setName(report.getName()+"电力安全测评报告");
        reportVO.setEmail(project.getEmail());
        reportVO.setLevel(report.getLevel());
        reportVO.setPhone(project.getPhone());
        reportVO.setSystem(report.getName());

        // 填充生成报告需要的数据：装配ReportVO
        // 填充List<Cla>
        List<Cla> clas = criterionService.getClas(reportVO.getrId());
        reportVO.setClas(clas);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("report", reportVO);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        WordUtil.generateWord(dataMap, outputStream);

        HttpHeaders headers = new HttpHeaders();
        //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
        // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
        String fileName = "test.doc";
        headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        headers.add("Content-Length", "" + outputStream.toByteArray().length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 将文件内容和头部信息封装到响应实体中并返回
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

}
