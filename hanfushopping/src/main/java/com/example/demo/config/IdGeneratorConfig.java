package com.example.demo.config;

import javax.sql.DataSource;

import org.quincy.rock.core.id.TableIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * <b>主键id自动生成器配置。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
@SpringBootConfiguration
@ConditionalOnProperty(prefix = "rock.idgen", name = "table")
public class IdGeneratorConfig {
	@Autowired
	private IdGeneratorProperties idGenProperties;

	@Bean
	public TableIdGenerator gen(DataSource dataSource) {
		TableIdGenerator idGenerator = new TableIdGenerator();
		idGenerator.setDataSource(dataSource);
		idGenerator.setTableName(idGenProperties.getTable());
		idGenerator.setInitialValue(idGenProperties.getInitial());
		idGenerator.setCache(idGenProperties.getCache());
		idGenerator.setKeyFieldName(idGenProperties.getKeyFieldName());
		idGenerator.setValueFieldName(idGenProperties.getValueFieldName());
		return idGenerator;
	}
}
