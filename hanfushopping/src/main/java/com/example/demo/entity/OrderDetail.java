package com.example.demo.entity;

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
@JoinTables({ @JoinTable(name = "t_order_table", alias = "o", onExpr = "o.f_id=od.f_order_id")})
public class OrderDetail extends Entity{
	private static final long serialVersionUID = 6832315456734074806L;


	@ApiModelProperty(value = "订单id", required = true, position = 1)
    private String orderId;

    @ApiModelProperty(value = "商品id", position = 2)
    private String productId;

    @ApiModelProperty(value = "商品数量", position = 3)
    private String quantity;
}
