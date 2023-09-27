package com.example.demo.entity;

import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.Table;
import com.example.demo.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品详情实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_order_detail", alias = "d")
public class Order_detail extends Entity{
	private static final long serialVersionUID = 6832315456734074806L;

	@ApiModelProperty(value = "订单编号", required = true, position = 1)
	private long order_id;
	
	@ApiModelProperty(value = "商品编号", required = false,position = 2)
	private long product_id;

	@ApiModelProperty(value = "商品数量", required = true,position = 3)
	private int quantity;

}
