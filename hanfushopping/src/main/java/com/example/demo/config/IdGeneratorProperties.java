package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * <b>IdGeneratorProperties。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rock.idgen")
public class IdGeneratorProperties {
	private String table; //维护自增主键的表名
	private long initial; //初始值
	private int cache; //缓冲主键个数
	private String KeyFieldName; //主键key字段名
	private String valueFieldName; //主键值字段名
}
