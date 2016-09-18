package com.hc.web;

import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

/**
 * Created by 诚 on 2016/9/13.
 */
public class Bootstrap extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //配置StandardServletMultipartResolver需要用这个
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("/",2097152L,4194304L,0));
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("utf-8");
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        //true表明由ServletContext管理
        delegatingFilterProxy.setTargetFilterLifecycle(true);
        //这一步在不用web.xml的情况下必不可少，表示我们要对实现了ShiroFilterFactoryBean的Bean类进行代理
        delegatingFilterProxy.setTargetBeanName("shiroFilter");
        return new Filter[]{delegatingFilterProxy,encodingFilter};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addFilter("shiroFilter",DelegatingFilterProxy.class);
    }
}
