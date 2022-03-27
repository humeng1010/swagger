# Swagger学习笔记

使用Swagger生成API， 我们可以得到交互式文档，自动生成代码的SDK以及API的发现特性等。

前后端分离的项目，接口文档的存在十分重要。与手动编写接口文档不同，swagger是一个自动生成接 口文档的工具，在需求不断变更的环境下，手动编写文档的效率实在太低。与swagger2相比新版的 swagger3配置更少，使用更加方便。

Swagger作用：

- 将项目中所有的接口展现在页面上，这样后端程序员就不需要专门为前端使用者编写专门的接口文 档；
- 当接口更新之后，只需要修改代码中的 Swagger 描述就可以实时生成新的接口文档了，从而规避 了接口文档老旧不能使用的问题；
- 通过 Swagger 页面，我们可以直接进行接口调用，降低了项目开发阶段的调试成本。

# 使用

1. 导入pom坐标依赖

```xml
<!--        导入swagger包-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
        <!--        包-->
```

2. 编写SwaggerConfig的配置类

```java
package com.meng.swaggerdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Configuration
@EnableOpenApi//开启swagger
@EnableWebMvc
public class SwaggerConfig {

}
```

3. 编写hello测试controller接口

```java
package com.meng.swaggerdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "hello测试类")//给html页面本类接口添加中文注释
@RestController
public class HelloController {
    @ApiOperation("测试方法")//中文注释方法
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}

```

4. 运行SpringBoot，并访问：http://localhost:8080//swagger-ui/index.html

5. 即可查看接口

# swagger常用注解

```
@Api：用在请求的类上，表示对类的说明 
    tags="说明该类的作用，可以在UI界面上看到的注解" 
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
    
@ApiOperation：用在请求的方法上，说明方法的用途、作用 
    value="说明方法的用途、作用" 
    notes="方法的备注说明"
    
@ApiImplicitParams：用在请求的方法上，表示一组参数说明 
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面 
        name：参数名 value：参数的汉字说明、解释 
        required：参数是否必须传 
        paramType：参数放在哪个地方 
        · header --> 请求参数的获取：@RequestHeader 
        · query --> 请求参数的获取：@RequestParam 
        · path（用于restful接口）--> 请求参数的获取：@PathVariable 
        · div（不常用） 
        · form（不常用） 
        dataType：参数类型，默认String，其它值dataType="Integer" 
        defaultValue：参数的默认值
        
@ApiResponses：用在请求的方法上，表示一组响应 
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 
        code：数字，例如400 
        message：信息，例如"请求参数没填好" 
        response：抛出异常的类
        
@ApiModel：用于响应类上，表示一个返回响应数据的信息 
            （这种一般用在post创建的时候，使用@RequestBody这样的场景， 
              请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性
```

## 实例一： @ApiImplicitParams 和 @ApiImplicitParam 参数描述

post方式，根据name和age两个参数查询数据，返回信息； 我们用 @ApiImplicitParams 和 @ApiImplicitParam ，描述请求参数

```java
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
    @ApiOperation("测试查询")//中文注释方法
    public String get(String name, Integer age) {
        return name + ":" + age;
    }

}

```

## 实例二 @ApiModel , @ApiModelProperty 实体参数描述

```java
package com.meng.swaggerdemo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户信息实体类
 */
@ApiModel("用户信息实体类")//描述实体类的
public class User {

    @ApiModelProperty("用户id")//描述属性
    private Integer id;
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("年龄")
    private Integer age;

    public User() {
    }

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

参数上，直接用 User user

```java

@Api(tags = "hello测试类")//给html页面本类接口添加中文注释
@RestController
public class HelloController {
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
}

```

## 实例三 @ApiResponses ， @ApiResponse

我们搞一个根据id获取用户信息案例，通过 @PathVariable 获取id，返回User对象，以及通过 @ApiResponses ， @ApiResponse ，描述响应码对应的描述信息

```java

@Api(tags = "hello测试类")//给html页面本类接口添加中文注释
@RestController
public class HelloController {
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

```

## Swagger3 API信息配置

我们要修改API信息默认配置的话，可以通过新建一个SwaggerConfig配置类，重写 ApiInfo 实现，以及重写 Docket 实现并且设置apiInfo；

```java
package com.meng.swaggerdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi//开启swagger
@EnableWebMvc
public class SwaggerConfig {
    /*** 配置swagger的Docket bean * @return */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)// 指定swagger3.0版本
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.meng.swaggerdemo.controller.dev1"))//让这个001组只能访问到dev1中的接口
//                .build()
//                .groupName("开发组001")//分组
                .enable(true)//开关swagger（只有在Dev环境开启）
                .apiInfo(createApiInfo());//设置个人信息
    }

    /**
     * 配置swagger的ApiInfo bean * @return
     * 直接去ApiInfo类中找到static静态代码块复制过来修改信息
     * static {
     * DEFAULT = new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
     * }
     * <p></p>
     * public ApiInfo(String title,
     * String description,
     * String version,
     * String termsOfServiceUrl,
     * Contact contact,
     * String license,
     * String licenseUrl,
     * Collection<VendorExtension> vendorExtensions)
     */
    @Bean
    public ApiInfo createApiInfo() {
        return new ApiInfo("User Api 文档",
                "用户信息",
                "3.0",
                "https://gitee.com/xiaohugitee",
                new Contact("小胡",
                        "https://gitee.com/xiaohugitee",
                        "1589029587@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
```

这个API信息主要作用是让前端开发人员看的，谁开发的接口，或者哪个小组负责，有问题方便联系沟 通；

## Swagger3 Docket 开关&过滤&分组 配置详解

我们可以通过设置Docket，可以配置很多功能，比如是否开启swagger，过滤，分组等；

### 开关设置enable

一般情况，我们只有在开发环境才会用到swagger，正式环境需要关闭swagger，一个是安全问题，还 有一个是用了swagger会影响系统运行速度； 我们通过设置Docket对象的enable即可；

```
/*** 配置swagger的Docket bean * @return */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)// 指定swagger3.0版本
                .enable(true)//开关swagger（只有在Dev环境开启）
                .apiInfo(createApiInfo());//设置个人信息
    }
```

设置后，重启项目，发现已经看不到API信息了；

### 设置过滤

有些情况，我们需要指定固定包路径下的类生成API，或者根据前端用户路径请求过滤； 使用过滤，必须先调用 select 方法； 通过apis方法， basePackage 可以根据包路径来生成特定类的API， any 方法是默认所有都有效，
none 方法都无效； withClassAnnotation 根据类注解， withMethodAnnotation 是根据方法注解； 一般我们用的是 basePackage 方法；

```java
package com.meng.swaggerdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi//开启swagger
@EnableWebMvc
public class SwaggerConfig {
    /*** 配置swagger的Docket bean * @return */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)// 指定swagger3.0版本
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meng.swaggerdemo.controller.dev1"))//设置过滤，让当前默认组只能访问这个dev中的接口
                .build()
                .enable(true);//开关swagger（只有在Dev环境开启）
    }
}

```

### 设置分组

在实际项目开发中，把复杂项目划分多模块给多个小组或者多个人负责开发，所以每个小组或者个人要 实现自己的分组，方便查找到API接口开发负责人，沟通和处理问题；

我们通过 groupName 方法可以设置组名；

我们结合前面学过的过滤，通过apis的basePackage方法，搞两个组，分别扫描不同的包路 径； 模拟分组开发，controller包下建两个子包，分别是one和two包，用来模拟两个业务模块；

```java
package com.meng.swaggerdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi//开启swagger
@EnableWebMvc
public class SwaggerConfig {
    //============组一=============

    /*** 配置swagger的Docket bean * @return */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)// 指定swagger3.0版本
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meng.swaggerdemo.controller.dev1"))//让这个001组只能访问到dev1中的接口
                .build()
                .groupName("开发组001")//分组
                .enable(true)//开关swagger（只有在Dev环境开启）
                .apiInfo(createApiInfo());//设置个人信息
    }

    /**
     * 配置swagger的ApiInfo bean * @return
     * 直接去ApiInfo类中找到static静态代码块复制过来修改信息
     * static {
     * DEFAULT = new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
     * }
     * <p></p>
     * public ApiInfo(String title,
     * String description,
     * String version,
     * String termsOfServiceUrl,
     * Contact contact,
     * String license,
     * String licenseUrl,
     * Collection<VendorExtension> vendorExtensions)
     */
    @Bean
    public ApiInfo createApiInfo() {
        return new ApiInfo("User Api 文档",
                "用户信息",
                "3.0",
                "https://gitee.com/xiaohugitee",
                new Contact("小胡",
                        "https://gitee.com/xiaohugitee",
                        "1589029587@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    //=============组二=============

    /**
     * dev2
     *
     * @return
     */
    @Bean
    public Docket createRestApi2() {
        return new Docket(DocumentationType.OAS_30)// 指定swagger3.0版本
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meng.swaggerdemo.controller.dev2"))//让这个001组只能访问到dev1中的接口
                .build()
                .groupName("开发组002")//分组
                .enable(true)//开关swagger（只有在Dev环境开启）
                .apiInfo(createApiInfo2());//设置个人信息
    }

    @Bean
    public ApiInfo createApiInfo2() {
        return new ApiInfo("User Api 文档",
                "用户信息",
                "3.0",
                "https://gitee.com/xiaohugitee",
                new Contact("xiaofeng",
                        "https://gitee.com/xiaohugitee",
                        "1589029587@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }


}

```

#### 更多功能等待探索......









