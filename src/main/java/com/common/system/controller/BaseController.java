package com.common.system.controller;

import com.common.system.shiro.ShiroKit;
import com.common.system.shiro.ShiroUser;
import com.common.system.util.Result;

/**
 * A base class for other controllers in a web application.
 */
public class BaseController {
    //Define two static variables for URL redirection(重定向) and forwarding(转发)
    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    //Retrieve(检索) the current user session using Apache Shiro framework and return the authenticated(经过身份验证) user
    protected ShiroUser getUser(){
        return (ShiroUser)ShiroKit.getSubject().getPrincipal();
    }

    //Build and return a standardized(标准的) response object with a status,message,status code,and data
    protected Result buildResult(boolean status,int code,String msg,Object o){
        Result result = new Result();
        result.setMsg(msg);
        result.setStatus(status);
        result.setCode(code);
        result.setData(o);
        return result;
    }
}
