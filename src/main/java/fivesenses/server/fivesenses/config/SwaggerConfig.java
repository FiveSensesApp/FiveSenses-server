package fivesenses.server.fivesenses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Fivesenses")
                .description("fivesenses API Document")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("fivesenses.server.fivesenses.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
//
//    private ApiInfo swaggerInfo() {
//        return new ApiInfoBuilder()
//                .title("fivesenses")
//                .description("fivesenses API Document")
//                .build();
//    }
//
//    @Bean
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .consumes(getConsumeContentTypes())
//                .produces(getProduceContentTypes())
//                .apiInfo(swaggerInfo()).select()
//                .apis(RequestHandlerSelectors.basePackage("fivesenses.server.fivesenses.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .useDefaultResponseMessages(false);
//    }
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//
//    private Set<String> getConsumeContentTypes() {
//        Set<String> consumes = new HashSet<>();
//        consumes.add("application/json;charset=UTF-8");
//        consumes.add("application/x-www-form-urlencoded");
//        return consumes;
//    }
//
//    private Set<String> getProduceContentTypes() {
//        Set<String> produces = new HashSet<>();
//        produces.add("application/json;charset=UTF-8");
//        return produces;
//    }


}