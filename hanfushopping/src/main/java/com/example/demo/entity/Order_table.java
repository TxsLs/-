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
@ApiModel(description = "订单实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_order_table", alias = "o")
public class Order_table extends Entity{
	
	private static final long serialVersionUID = 6839230938734065812L;
	
	@IgnoreInsert
	@ApiModelProperty(value = "账号", required = false,position = 1)
	private String f_code;
	
	@IgnoreInsert
	@ApiModelProperty(value = "顾客编号", required = true,position = 2)
	private Long customer_id;

	@IgnoreInsert
	@ApiModelProperty(value = "订单总价", required = true,position = 3)
	private float total_price;
	
	@IgnoreInsert
	@ApiModelProperty(value = "订单状态", required = true, position = 4)
	private String order_status;
	
	@ApiModelProperty(value = "订单地址", required = true,position = 5)
	private String address;
	
	@IgnoreInsert
	@ApiModelProperty(value = "创建时间", required = true,position = 6)
	private Date created_time;

	@IgnoreInsert
	@ApiModelProperty(value = "更新时间", position = 7)
	private Date updated_time;
	

}
