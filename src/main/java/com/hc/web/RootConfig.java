package com.hc.web;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.datasources.PerUserPoolDataSource;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.apache.log4j.Logger;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.*;
import org.springframework.core.env.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by 诚 on 2016/9/13.
 */
@Configuration
@ComponentScan(basePackages = {"com.hc"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
//@PropertySource("classpath:datasource.properties")
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true)
public class RootConfig {
//    @Autowired
//    private Environment env;
    Logger logger = Logger.getLogger(this.getClass());
//    @Value("${JdbcDriverClassName}")
    private String jdbcDriverClassName = "com.mysql.jdbc.Driver";

//    @Value("${JdbcUsername}")
    private String jdbcUsername = "root";

//    @Value("${JdbcUrl}")
    private String jdbcUrl = "jdbc:mysql://localhost:3306/shiro?useSSL=true&charset=utf-8";

//    @Value("${JdbcPassword}")
    private String jdbcPassword = "root";

//    c3p0数据连接池
//    @Bean
//    public DataSource _dataSource() throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(jdbcDriverClassName);
//        dataSource.setJdbcUrl(jdbcUrl);
//        dataSource.setUser(jdbcUsername);
//        dataSource.setPassword(jdbcPassword);
////        dataSource.setAutoCommitOnClose(false);
//        dataSource.setMaxPoolSize(30);
//        dataSource.setMinPoolSize(10);
//        dataSource.setCheckoutTimeout(10000);
//        dataSource.setAcquireRetryAttempts(2);
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
//        dataSource.setDriverClassName(env.getProperty("JdbcDriverClassName"));
//        dataSource.setUrl(env.getProperty("JdbcUrl"));
//        dataSource.setUsername(env.getProperty("JdbcUsername"));
//        dataSource.setPassword(env.getProperty("JdbcPassword"));
        dataSource.setMaxActive(30);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new ClassPathResource("config.xml"));
        sqlSessionFactory.setMapperLocations(new ClassPathResource[]{new ClassPathResource("mapper/UserDao.xml"),
                                                                        new ClassPathResource("mapper/RoleDao.xml"),
                                                                        new ClassPathResource("mapper/PermissionDao.xml")});
        sqlSessionFactory.setTypeAliasesPackage("com.hc.model");

        return sqlSessionFactory;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.hc.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        logger.debug("--------------------------->MapperScannerConfigurer启动了");
        return mapperScannerConfigurer;
    }

}
