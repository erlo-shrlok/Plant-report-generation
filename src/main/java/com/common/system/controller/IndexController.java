package com.common.system.controller;

import com.common.system.entity.RcMenu;
import com.common.system.entity.RcPrivilege;
import com.common.system.service.MenuService;
import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.MenuComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller is responsible(负责) for redering(呈现) the main page of an administrative(管理的) system,
 * with the appropriate(适当的) menus and privileges(权限) shown to the currently logged in user.
 */
@RestController
public class IndexController {
    // The "@Autowired" annotation is used to inject(注入) the "MenuService" Object into the controller class
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView){
        // Retrieve(检索、获取) the currently logged in user`s privileges using ShiroKit
        ShiroUser user = (ShiroUser) ShiroKit.getSubject().getPrincipal();

        // Set the view name to "/system/admin/index"
        modelAndView.setViewName("/system/admin/index");

        // Get a list of the user`s privileges
        List<RcPrivilege> privilegeList = user.getPrivilegeList();

        // If the user has privileges
        if (null != privilegeList){

            // Create a list to store the unique IDs of the menus that the user has access to
            List<String> ids = new ArrayList<>();

            // Loop through the user`s privileges
            for (RcPrivilege p : privilegeList){

                // If the menu ID is not already in the list, add it
                if (!ids.contains(p.getMenuId())){
                    ids.add(p.getMenuId());
                }
            }

            // Create a list to store the menu types that we want to retrieve
            List<Integer> wantList = new ArrayList<>();
            //得到一级菜单
            // Add the ID for top-level menus (type 1) to the list
            wantList.add(1);
            // Get a list of top-level menus that the user has access to
            List<RcMenu> menuList = menuService.selectInIds(ids,wantList);
            // Clear the wantList so that we can use it again
            wantList.clear();
            // If there are top-level menus that user can access
            if (menuList != null){
                //得到二级菜单
                // Add the ID for second-level menus (type 2) to the list
                wantList.add(2);
                // Get a list of second-level menus that the user has access to
                List<RcMenu> secondMenuList = menuService.selectInIds(ids,wantList);
                // Loop through the top-level menus
                for (RcMenu menu:menuList) {
                    // Create a list to store the child menus of the current top-level menus
                    List<RcMenu> childList = new ArrayList<>();
                    // Loop through the second-level menus
                    for (RcMenu nu:secondMenuList
                         ) {
                        // If the second-level menu is a child of the current top-level menu, add it to the childList
                        if (menu.getId().equals(nu.getpId())){
                            childList.add(nu);
                        }
                    }
                    // Sort the childList using the MenuComparator class
                    childList.sort(new MenuComparator());
                    menu.setChild(childList);
                }
                //Sort the menuList
                menuList.sort(new MenuComparator());
                modelAndView.addObject("menuList",menuList);
            }
        }
        return modelAndView;
    }
}
