package com.common.system.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p>记录操作数据库的日志</p>
 * @Aspect 是一个用于定义切面的注解，它告诉Spring框架将该类视为一个切面。它允许开发者在应用程序中定义通用的行为，例如日志记录、性能检测、安全检查等。
 * @Component 是一个用于定义组件的注解，他告诉Spring框架将该类视为一个组件。
 * @Pointcut 定义切入点，即需要拦截的方法
 * @AfterReturning 定义通知，即切面在拦截到方法返回结果后执行的操作。
 */
@Aspect
@Component
public class LogAspect {

    public void update(){

    }
//    @Pointcut("execution(* com.common.system.service.impl.*.*(..))")
    public void select(){
        System.out.println("...........................................................");
    }
//    @AfterReturning(value = "select()")
    public void select1(){
        System.out.println("...........................................................");
    }
}
