package com.cube.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.cube.kiosk"))//填写自己controller位置
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("lijin", "http://localhost:8080/", "317395616@qq.com");
        return new ApiInfoBuilder().title("自助机API")
                .description("相关描述")
                .contact(contact).version("1.0").build();
    }
}
