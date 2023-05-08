package com.common.system.controller;

import com.common.system.entity.Project;
import com.common.system.service.ProjectService;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.PageBean;
import com.common.system.util.Result;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * 获取当前用户的所有项目id及其name
     * @return
     */
    @ResponseBody
    @RequestMapping("getIdAndName")
    public List<Project> getIN(){
        Subject subject = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        Integer uid = user.getId();
        return projectService.getIN(uid);
    }

    /**
     * list页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/project/list");
        return modelAndView;
    }

    /**
     * add页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/project/add");
        return modelAndView;
    }

    /**
     * 保存一条project信息
     * @param project
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public @ResponseBody Result save(Project project){
        Subject subject = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        Integer uid = user.getId();
        project.setuId(uid);
        Integer i = projectService.add(project);
        return Result.add(i);
    }

    /**
     * page页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "page",method = RequestMethod.GET)
    public ModelAndView queryForPage(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/project/page");
        return modelAndView;
    }

    /**
     * 分页展示project信息
     * @param start
     * @param pageSize
     * @param date
     * @param search
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "page")
    public PageBean<Project> queryForPage(@RequestParam(value = "start", defaultValue = "1") int start,
                                         @RequestParam(value = "length", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "date", required = false) String date,
                                         @RequestParam(value = "search", required = false) String search,
                                         HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        PageInfo<Project> pageInfo = projectService.listForPage((start / pageSize) + 1, pageSize);
        return new PageBean<Project>(pageInfo);
    }

    /**
     * 修改页面
     * @param id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id, ModelAndView modelAndView) {
        Result<Project> result = projectService.getById(id);
        modelAndView.addObject("bean", result.getData());
        modelAndView.setViewName("/system/admin/project/edit");
        return modelAndView;
    }

    /**
     * 更新一条project数据
     * @param project
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Result update(Project project) {
        Subject subject = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        Integer uid = user.getId();
        project.setuId(uid);
        Result result = projectService.update(project);
        return result;
    }

    /**
     * 删除一条project数据
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Result delete(@PathVariable Integer id) {
        Result<Integer> result = projectService.deleteById(id);
        return result;
    }

}
