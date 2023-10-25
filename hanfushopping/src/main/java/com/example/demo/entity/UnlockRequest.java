package com.example.demo.entity;

import java.util.Date;


import org.quincy.rock.core.dao.annotation.Table;

import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>UnlockRequest。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Getter
@Setter
@ApiModel(description = "解封表实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_unlock_request", alias = "ur")
public class UnlockRequest extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1045277986084614700L;

	@ApiModelProperty(value = "封禁者编号", required = true, position = 1)
	private String code;

	@ApiModelProperty(value = "封禁理由", required = true, position = 2)
	private String name;

	@ApiModelProperty(value = "封禁理由", required = true, position = 3)
	private Long phone;

	@ApiModelProperty(value = "封禁起始时间", position = 4)
	private Date requestTime;

	@ApiModelProperty(value = "封禁理由", required = true, position = 5)
	private String reason;

	@ApiModelProperty(value = "封禁理由", required = true, position = 6)
	private Integer type;

	@ApiModelProperty(value = "封禁理由", required = true, position = 7)
	private Integer status;

	@ApiModelProperty(value = "封禁理由", required = true, position = 8)
	private Date processingTime;
	
	@ApiModelProperty(value = "封禁商品id", required = true, position = 9)
	private Long productId;
}
