package com.example.demo.entity;

import java.sql.Date;

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
	@ApiModelProperty(value = "订单编号", required = true, position = 1)
	private Long order_id;

	@ApiModelProperty(value = "用户编号", required = true, position = 2)
	private Long user_id;

	@ApiModelProperty(value = "商家编号", required = true,position = 3)
	private Long merchant_id;

	@ApiModelProperty(value = "商品编号", required = true,position = 4)
	private Long product_id;
	
	@ApiModelProperty(value = "商品数量", required = true,position = 5)
	private int quantity;
	
	@ApiModelProperty(value = "订单总价", required = true,position = 6)
	private int total_price;

	@ApiModelProperty(value = "订单状态", required = true, position = 7)
	private Boolean order_status;
	
	@ApiModelProperty(value = "用户所选地址", required = true,position = 8)
	private String address_id;

	@ApiModelProperty(value = "创建时间", required = true,position = 9)
	private Date created_time;

	@ApiModelProperty(value = "更新时间", position = 10)
	private Date updated_time;
	
	@ApiModelProperty(value = "检索字段", required = false,position = 11)
	private int f_code;
}
