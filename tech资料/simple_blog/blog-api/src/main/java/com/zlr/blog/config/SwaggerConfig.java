package com.zlr.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.config
 * @Description
 * @create 2022-08-06-下午4:04
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 是否开启swagger,默认开启
     */
    @Value(value = "${swagger.enabled:true}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameterList = new ArrayList<>();
        parameterBuilder.name("Authorization").description("格式：Bearer access_token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        parameterList.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zlr.blog"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BLOG RESTFUL API")
                .description("BLOG RESTFUL API")
                .contact(new Contact("zenglr","http://baidu.com","18023893551@163.com"))
                .version("2.0")
                .build();
    }

}
