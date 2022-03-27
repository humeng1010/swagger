package com.meng.swaggerdemo.controller.dev2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "hello测试类")//给html页面本类接口添加中文注释
@RestController
public class HelloController2 {
    @ApiOperation("测试方法2")//中文注释方法
    @GetMapping("/hello2")
    public String hello() {
        return "hello2";
    }


}
