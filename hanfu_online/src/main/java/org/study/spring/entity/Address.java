package org.study.spring.entity;

import org.study.spring.Entity;

import io.swagger.annotations.ApiModelProperty;

public class Address extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 7255324056018475158L;

	@ApiModelProperty(value = "代码", required = true, position = 1)
	private String code;

	@ApiModelProperty(value = "顾客id", required = true, position = 2)
	private String customerId;

	@ApiModelProperty(value = "地址", position = 3)
	private String address;
}
