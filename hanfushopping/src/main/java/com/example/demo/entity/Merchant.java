package com.example.demo.entity;

import java.util.Date;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.IgnoreInsertUpdate;
import org.quincy.rock.core.dao.annotation.Table;
import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商家实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_merchant", alias = "m")
public class Merchant extends Entity {
	
	private static final long serialVersionUID = 6839230938734074806L;

	@ApiModelProperty(value = "用户名", required = true, position = 1)
	private String name;
	
	@ApiModelProperty(value = "地址", required = false,position = 2)
	private String merchantAddress;

	@ApiModelProperty(value = "创建时间", required = true,position = 3)
	private Date createdTime;

	@ApiModelProperty(value = "商家描述", required = true,position = 4)
	private String merchantIntroduction;

	@ApiModelProperty(value = "是否违规", required = true, position = 5)
	@IgnoreInsertUpdate
	private Integer isviolate;

	@ApiModelProperty(value = "密码", required = false,position = 6)
	private String merchantPassword;

	@ApiModelProperty(value = "账号", required = false,position = 7)
	private String code;

	@ApiModelProperty(value = "手机号", required = false,position = 8)
	private Long phone;

	@ApiModelProperty(value = "邮箱", required = false,position = 9)
	private String email;
	
	@ApiModelProperty(value = "返回是否有照片", position = 10)
	@Column(value = "f_photo_file is not null", calculated = true)
	private Boolean hasPhoto;
	
}
