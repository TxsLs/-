package com.example.demo.entity;

import java.util.Date;

import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.Table;
import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商家实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_merchant", alias = "m")
public class Merchant extends Entity {
	
	private static final long serialVersionUID = 683923093074806L;

	@ApiModelProperty(value = "名称", required = true, position = 1)
	private String name;
	
	@IgnoreInsert
	@ApiModelProperty(value = "地址", required = false,position = 2)
	private String merchant_address;

	@IgnoreInsert
	@ApiModelProperty(value = "创建时间", required = true,position = 3)
	private Date created_time;

	@IgnoreInsert
	@ApiModelProperty(value = "商家描述", required = true,position = 4)
	private String introduction;

	@IgnoreInsert
	@ApiModelProperty(value = "是否违规", required = true, position = 5)
	private int isviolate;

	@IgnoreInsert
	@ApiModelProperty(value = "密码", required = false,position = 6)
	private String password;

	@IgnoreInsert
	@ApiModelProperty(value = "账号", required = false,position = 7)
	private String code;

}
