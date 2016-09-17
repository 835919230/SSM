package com.hc.web;

import org.apache.ibatis.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;

/**
 * Created by 诚 on 2016/9/13.
 */
@EnableWebMvc
@Configuration
@ComponentScan("com.hc.web")
public class ServletConfig extends WebMvcConfigurerAdapter{
    Logger log = Logger.getLogger(this.getClass());
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        log.debug("ViewResolver启动");
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        //以下配置CommonsMultipartResolver就需要
//        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/hc/uploads"));
//        multipartResolver.setMaxUploadSize(2097152L);
//        multipartResolver.setMaxInMemorySize(4096);
        return multipartResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
