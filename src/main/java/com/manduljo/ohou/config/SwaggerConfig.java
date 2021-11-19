package com.manduljo.ohou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        List global = new ArrayList();
        global.add(new ParameterBuilder().name("Authorization").
                description("Access Token ex)Bearer yourtoken").parameterType("header").
                required(false).modelRef(new ModelRef("string")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(global)
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.ant("/api/**")) // 그중 /api/** 인 URL들만 필터링
                .build();
    }

    @Bean
    public Docket mongoApi() {
        List<Parameter> global = new ArrayList<>();
        global.add(
            new ParameterBuilder()
                .name("Authorization")
                .description("Access Token ex)Bearer yourtoken")
                .parameterType("header")
                .required(false)
                .modelRef(new ModelRef("string"))
                .build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(global)
                .groupName("mongo")
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.ant("/mongo-api/**")) // 그중 /mongo-api/** 인 URL들만 필터링
                .build();
    }
}
