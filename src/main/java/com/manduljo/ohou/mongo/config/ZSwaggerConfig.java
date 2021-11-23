package com.manduljo.ohou.mongo.config;

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
public class ZSwaggerConfig {

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
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/mongo-api/**"))
                .build();
    }

}
