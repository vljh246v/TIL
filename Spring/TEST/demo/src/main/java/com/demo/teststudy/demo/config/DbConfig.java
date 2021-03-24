package com.demo.teststudy.demo.config;


import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.demo.teststudy.demo.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DbConfig {

  @Bean (name = "sqlSessionFactory")
  public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {

    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

    factory.setDataSource(dataSource);
    factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));

    return factory.getObject();
  }
}
