package com.shop.ordersystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Только добавляем, если хотим кастомные папки (js, images и т.п.)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // WebJars (Bootstrap из WebJars)
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // Стили и скрипты вашего приложения
        registry
                .addResourceHandler("/css/**", "/js/**", "/images/**")
                .addResourceLocations("classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/images/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
}
