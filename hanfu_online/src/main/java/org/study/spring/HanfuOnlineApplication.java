package org.study.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//@SuppressWarnings("deprecation")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@MapperScan(basePackages = "org.study.spring.dao")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class HanfuOnlineApplication {

	public static void main(String[] args) {
		//如果写在前面会执行两次，可能是依赖中热部署包:spring-boot-devtools的问题
		SpringApplication.run(HanfuOnlineApplication.class, args);
		System.out.println("/**\n"+
				" *      ┌─┐       ┌─┐\n"+
				" *   ┌──┘ ┴───────┘ ┴──┐\n"+
				" *   │                 │\n"+
				" *   │       ───       │\n"+
				" *   │  ─┬┘       └┬─  │\n"+
				" *   │                 │\n"+
				" *   │       ─┴─       │\n"+
				" *   │                 │\n"+
				" *   └───┐         ┌───┘\n"+
				" *       │         │\n"+
				" *       │         │\n"+
				" *       │         │\n"+
				" *       │         └──────────────┐\n"+
				" *       │                        │\n"+
				" *       │                        ├─┐\n"+
				" *       │                        ┌─┘\n"+
				" *       │                        │\n"+
				" *       └─┐  ┐  ┌───────┬──┐  ┌──┘\n"+
				" *         │ ─┤ ─┤       │ ─┤ ─┤\n"+
				" *         └──┴──┘       └──┴──┘\n"+
				" *                神兽保佑\n"+
				" *               代码无BUG!\n"+
				"  **/\n");
	}

}

