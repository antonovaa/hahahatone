package ru.gameserver.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class JdbcConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/hackatone");
        dataSource.setUsername("postgres");
        dataSource.setPassword("hackatone");
        return dataSource;
    }

//
//    @Configuration
//    @EnableSwagger2
//    public class SwaggerConfig extends WebMvcConfigurationSupport {
//
//        @Bean
//        public Docket productApi() {
//            return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("ru.gameserver.controller"))
//                .paths(regex("/*.*"))
//                .build();
//        }
//        @Override
//        protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//            registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//            registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");
//                            registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/static/");
//
//        }
//
//    }
//
//    @Configuration
//    @EnableSwagger2
//    public static class SwaggerConfig extends WebMvcConfigurationSupport {
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/static/");
//            registry.addResourceHandler("swagger-ui.html")
//               .addResourceLocations("classpath:/resources/");
//
//            registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//            registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");
//        }
//    }


}
