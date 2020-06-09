package config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * XXX模块接口
     * @return Docket对象
     */
    @Bean
    public Docket XXXApi(){
        Docket docket=new Docket(DocumentationType.SWAGGER_2);
        ApiSelectorBuilder selectorBuilder=docket.groupName("XXX")
                .apiInfo(apiInfo()).select();
        selectorBuilder.apis(RequestHandlerSelectors.basePackage("com.XXX.controller"));
        selectorBuilder.paths(PathSelectors.any()).build();
        return docket;
    }

    /**
     * Api信息
     * @return Api信息
     */
    private ApiInfo apiInfo(){
        ApiInfoBuilder apiInfoBuilder=new ApiInfoBuilder();
        apiInfoBuilder.title("XXX平台Api接口文档");
        apiInfoBuilder.contact(new Contact("lp","",""));
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.description("该文档为XXX平台系统接口文档");
        return apiInfoBuilder.build();
    }
}
