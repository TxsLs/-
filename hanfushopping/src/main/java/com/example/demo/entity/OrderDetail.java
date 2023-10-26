package com.example.demo.entity;

import java.math.BigDecimal;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.IgnoreUpdate;
import org.quincy.rock.core.dao.annotation.JoinTable;
import org.quincy.rock.core.dao.annotation.JoinTables;
import org.quincy.rock.core.dao.annotation.Table;
import com.example.demo.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品详情实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_order_detail", alias = "od")
@JoinTables({ @JoinTable(name = "t_order_table", alias = "o", onExpr = "o.f_id=od.f_order_id"),
		@JoinTable(name = "t_product", alias = "p", onExpr = "od.f_product_id=p.f_id") })
public class OrderDetail extends Entity {
	private static final long serialVersionUID = 6832315456734074806L;

	@ApiModelProperty(value = "订单id", required = true, position = 1)
	private String orderId;

	@ApiModelProperty(value = "商品id", position = 2)
	private String productId;

	@ApiModelProperty(value = "商品数量", position = 3)
	private String quantity;

	@ApiModelProperty(value = "商品名称", position = 4)
	@Column(value = "f_name", tableAlias = "p", ignoreInsert = true, ignoreUpdate = true)
	private String productName;

	@ApiModelProperty(value = "商品价格", position = 5)
	@Column(value = "f_price", tableAlias = "p", ignoreInsert = true, ignoreUpdate = true)
	private String productPrice;

	@ApiModelProperty(value = "商家id", position = 6)
	@Column(value = "f_merchant_id", tableAlias = "p", ignoreInsert = true, ignoreUpdate = true)
	private String merchantId;
	
	@ApiModelProperty(value = "顾客地址", position = 7)
	@Column(value = "f_address", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private String address;
	
	@ApiModelProperty(value = "订单code", position = 8)
	@Column(value = "f_code", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private String code;
	
	@ApiModelProperty(value = "订单总价", position = 9)
	@Column(value = "f_total_price", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private BigDecimal totalPrice;
	
	@ApiModelProperty(value = "订单状态", position = 10)
	@Column(value = "f_order_status", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private String orderStatus;
	
	
	@ApiModelProperty(value = "创建时间", position = 11)
	@Column(value = "f_created_time", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private String createdTime;

	@ApiModelProperty(value = "更新时间", position = 12)
	@Column(value = "f_updated_time", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private String updatedTime;
	
	@ApiModelProperty(value = "手机号", position = 13)
	@Column(value = "f_phone", tableAlias = "o", ignoreInsert = true, ignoreUpdate = true)
	private Long phone;
	
}
