package org.study.spring.entity;

import java.sql.Date;

import org.study.spring.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>退货管理</b>
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
public class Refund  extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 3490363017160236991L;

	@ApiModelProperty(value = "订单编号", position = 1)
    private String orderId;
	
	@ApiModelProperty(value = "申请时间", position = 2)
    private Date requestTime;
	
	@ApiModelProperty(value = "退货理由", position = 3)
    private String reason;
	
	@ApiModelProperty(value = "退货状态", position = 4)
    private String refundStatus;
	
	@ApiModelProperty(value = "退款编号", position = 5)
    private String code;
	
	
	
}
