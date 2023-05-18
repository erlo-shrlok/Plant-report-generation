package com.common.system.config.mybatisplus;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @MapperScan 指定了需要扫描的Mapper接口所在的包路径
 * @Configuration 标注该类为配置类，Spring 在启动时会读取该类中的配置信息并进行相应的初始化
 *
 * MyBatis-Plus 在启动时自动扫描Mapper接口并创建对应的实现类，并将其注册为Spring Bean
 *
 */
@Configuration
@MapperScan(value = "com.common.system.mapper")
public class MybatisPlusConfig {
    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     *
     * 配置MyBatis-Plus提供的分页插件
     * @Bean 表示将返回的PaginationInterceptor对象注册为 Spring Bean，使得其他组件可以直接注入该对象进行使用
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
