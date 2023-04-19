package com.example.CourseRecommendation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
////        String path = "file:F:/jarTest/resources/static/img/";
////        registry.addResourceHandler("/img/**").addResourceLocations("file:" + path);
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/img/**")
//                    .addResourceLocations("file:F:/jarTest/resources/static/img/");
//        }
////        String path = MyConfig.RESOURCE_PATH+"static\\img";
////        registry.addResourceHandler("/img/**").addResourceLocations("file:" + path);
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:"+MyConfig.RESOURCE_PATH+"static/img/");
    }
}

