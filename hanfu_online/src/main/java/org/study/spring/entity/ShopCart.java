package org.study.spring.entity;

import java.math.BigDecimal;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.JoinTable;
import org.quincy.rock.core.dao.annotation.Table;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>购物车实体</b>
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
@ApiModel(description = "购物车实体")
@Table(name = "t_shopcart", alias = "sc")
@JoinTable(name = "t_product", alias = "p", onExpr = "p.f_id=sc.f_product_id")
public class ShopCart extends Entity {


	
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 5157005842651527005L;

	@ApiModelProperty(value = "顾客编号", position = 1)
    private String customerCode;
	
	@ApiModelProperty(value = "商品编号", position = 2)
    private Long productId;	
	
	@ApiModelProperty(value = "商品数量", position = 3)
    private Integer quantity;	

    @ApiModelProperty(value = "商品单价", position = 4)
	@Column(value = "f_price", tableAlias = "p", ignoreInsert = true, ignoreUpdate = true)
	private String price;


}
