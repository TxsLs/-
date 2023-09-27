package org.study.spring.entity;

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
@Table(name = "t_shopcart", alias = "a")
public class ShopCart extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3786859216339013592L;
	
	@ApiModelProperty(value = "顾客编号", position = 1)
    private String customerId;
	
	@ApiModelProperty(value = "商品编号", position = 1)
    private String productId;	
	
	@ApiModelProperty(value = "商品数量", position = 1)
    private String quantity;	
	
	@ApiModelProperty(value = "购物车编号", position = 1)
    private String code;	

	
}
