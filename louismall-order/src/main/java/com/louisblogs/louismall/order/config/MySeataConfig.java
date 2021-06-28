package com.louisblogs.louismall.order.config;

import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/28 19:54
 */

public class MySeataConfig {

//	@Autowired
//	DataSourceProperties dataSourceProperties;
//
//    @Bean
//    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
//        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        if (StringUtils.hasText(dataSourceProperties.getName())) {
//            dataSource.setPoolName(dataSourceProperties.getName());
//        }
//        return new DataSourceProxy(dataSource);
//    }

}
