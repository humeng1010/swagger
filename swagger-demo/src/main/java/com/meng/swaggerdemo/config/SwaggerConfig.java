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
