package com.common.system;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Shiro 认证过程
 */
public class AuthenticationTests {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before//在方法开始前添加一个用户
    public void addUser(){
        simpleAccountRealm.addAccount("jinzunyue","111");
    }

    @Test
    public void testAuthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);//设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject();//获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("jinzunyue","111");
        subject.login(token);//登录

        //subject.isAuthenticated() 方法返回一个boolean值，用于判断用户是否认证成功
        System.out.println("isAuthenticated:"+subject.isAuthenticated());//输出true

        subject.logout();//登出

        System.out.println("isAuthenticated:"+subject.isAuthenticated());//输出false
    }

}
