package org.study.spring.entity;

import org.quincy.rock.core.dao.annotation.Table;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>地址实体</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author Nymphet
 * @since 1.0
 */
@Getter
@Setter
@ApiModel(description = "地址实体")
@Table(name = "t_address", alias = "a")
public class Address extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 7255324056018475158L;

	@ApiModelProperty(value = "顾客id", required = true, position = 1)
	private String customerCode;

	@ApiModelProperty(value = "地址", position = 2)
	private String address;
}
