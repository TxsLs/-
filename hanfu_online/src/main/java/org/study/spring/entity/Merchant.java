package org.study.spring.entity;

import java.sql.Date;

import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.IgnoreUpdate;
import org.quincy.rock.core.dao.annotation.Table;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>商家实体</b>
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
@ApiModel(description = "商家实体")
@Table(name = "t_merchant", alias = "m")
public class Merchant extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1634502601769925129L;

	@ApiModelProperty(value = "名称", required = true, position = 1)
	private String name;
	
	@IgnoreInsert
	@ApiModelProperty(value = "地址", required = false,position = 2)
	private String merchant_address;

	@IgnoreInsert
	@ApiModelProperty(value = "创建时间", required = true,position = 3)
	private Date created_time;

	@IgnoreInsert
	@ApiModelProperty(value = "商家描述", required = true,position = 4)
	private String introduction;

	@IgnoreInsert
	@ApiModelProperty(value = "是否违规", required = true, position = 5)
	private int isviolate;

	@IgnoreInsert
	@ApiModelProperty(value = "密码", required = false,position = 6)
	private String password;

	@IgnoreInsert
	@ApiModelProperty(value = "账号", required = false,position = 7)
	private String code;


}
