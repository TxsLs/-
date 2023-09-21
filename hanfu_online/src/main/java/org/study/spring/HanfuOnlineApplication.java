package org.study.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//@SuppressWarnings("deprecation")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
@MapperScan(basePackages = "org.study.spring.dao")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class HanfuOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanfuOnlineApplication.class, args);
	}

}

