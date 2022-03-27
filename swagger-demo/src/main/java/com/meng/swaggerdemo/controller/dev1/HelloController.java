package com.meng.swaggerdemo.controller.dev1;

import com.meng.swaggerdemo.pojo.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "hello测试类")//给html页面本类接口添加中文注释
@RestController
public class HelloController {
    @ApiOperation("测试方法1")//中文注释方法
    @GetMapping("/hello1")
    public String hello() {
        return "hello1";
    }


    /**
     * 测试查询
     *
     * @param name
     * @param age
     * @return
     */
    @PostMapping("/search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, paramType = "query", dataType = "Integer")
    })
    @ApiOperation("测试查询")
    public String get(String name, Integer age) {
        return name + ":" + age;
    }

    /**
     * 测试添加
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("测试添加用户")
    public String add(User user) {
        return user.getName() + ":" + user.getAge();
    }

    @GetMapping("/user/{id}")
    @ApiOperation("根据id获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", required = true, paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 408, message = "指定业务报错信息，返回客户端"),
            @ApiResponse(code = 400, message = "请求参数没填写好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不正确")
    })
    public User load(@PathVariable("id") Integer id) {
        return new User(id, "jack", 22);
    }
}
