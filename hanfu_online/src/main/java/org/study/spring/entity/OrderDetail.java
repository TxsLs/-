package org.study.spring.entity;

import org.study.spring.Entity;

import io.swagger.annotations.ApiModelProperty;

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
public class OrderDetail extends Entity {
		
		/**
		 * serialVersionUID。
		 */
		private static final long serialVersionUID = 6839230938734074806L;

		@ApiModelProperty(value = "编号", position = 0)
	    private String code;
		
		@ApiModelProperty(value = "订单id", required = true, position = 1)
	    private String orderId;

	    @ApiModelProperty(value = "商品id", position = 2)
	    private String productId;

	    @ApiModelProperty(value = "商品数量", position = 3)
	    private String quantity;
}
