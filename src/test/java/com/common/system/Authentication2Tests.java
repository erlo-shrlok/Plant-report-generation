package com.common.system;

import com.common.system.shiro.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Shiro 授权过程
 */
public class Authentication2Tests {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before //在方法开始前添加一个用户，让它具备admin和user两个角色
    public void addUser(){
        simpleAccountRealm.addAccount("jinzunyue","111","admin","user");
    }

    @Test
    public void ScreenPosition(){
        try {
            Thread.sleep(2000);
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            System.out.println("Mouse location: x = "+mouseLocation.getX()+",y = "+mouseLocation.getY());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);// 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject();// 获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("jinzunyue","111");
        subject.login(token);//登录

        //subject.isAuthenticated()方法返回一个boolean值，用于判断用户是否认证成功
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        subject.checkRoles("admin","user");
    }

    @Test
    public void testAuthentication2(){
        MyRealm myRealm = new MyRealm();//实现自己的Real实例

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);//设置SecurityManager环境
        Subject subject= SecurityUtils.getSubject();//获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("jinzunyue","111");
        subject.login(token);//登录

        //subject.isAuthenticated()方法返回一个boolean值，用于判断用户是否认证成功
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        subject.checkRoles("admin","user");
        subject.checkPermission("user:add");
    }
}
