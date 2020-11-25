package com.imooc.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * IDEA 调试技巧
 * 断点统一开关
 * 条件断点
 * 单步调试
 * 表达式求值
 */
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.mall.model.dao")
@EnableSwagger2
//@EnableCaching
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

}
