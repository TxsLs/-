package org.study.spring.entity;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.JoinTable;
import org.quincy.rock.core.dao.annotation.JoinTables;
import org.quincy.rock.core.dao.annotation.Table;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>订单详情实体。</b>
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
@ApiModel(description = "订单详情实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_order_detail", alias = "od")
@JoinTables({ @JoinTable(name = "t_order_table", alias = "o", onExpr = "o.f_id=od.f_order_id") ,
@JoinTable(name = "t_product", alias = "p", onExpr = "od.f_product_id=p.f_id")}
)
public class OrderDetail extends Entity {

	/**
	* serialVersionUID。
	*/
	private static final long serialVersionUID = -8608451321835917597L;

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
	
	
}
