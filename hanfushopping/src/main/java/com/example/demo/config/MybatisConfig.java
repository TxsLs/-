package com.example.demo.config;

import java.util.Properties;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b>MybatisConfig。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@Configuration
public class MybatisConfig {

	@Bean
	public DatabaseIdProvider getDatabaseIdProvider() {
		DatabaseIdProvider provider = new VendorDatabaseIdProvider();
		Properties p = new Properties();
		p.setProperty("MySQL", "mysql");
		p.setProperty("Oracle", "oracle");
		p.setProperty("SQL Server", "sqlserver");
		p.setProperty("DB2", "db2");
		provider.setProperties(p);
		return provider;
	}
}
