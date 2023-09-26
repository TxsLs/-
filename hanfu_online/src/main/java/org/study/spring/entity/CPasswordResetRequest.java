package org.study.spring.entity;

import org.study.spring.Entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <b>顾客找回密码申请表</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author Nymphet
 * @since 1.0
 */
public class CPasswordResetRequest extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -8001566964531494449L;

	@ApiModelProperty(value = "顾客id", required = true, position = 1)
	private String cId;

	@ApiModelProperty(value = "地址",required = true, position = 2)
	private String requestTime;
	
	@ApiModelProperty(value = "申请状态", position = 3)
	private String cStatus;
	
	@ApiModelProperty(value = "手机号", position = 4)
	private String cPhone;
	
	@ApiModelProperty(value = "代码", position = 5)
	private String code;

}
