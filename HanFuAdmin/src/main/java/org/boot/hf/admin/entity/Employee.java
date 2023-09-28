package org.boot.hf.admin.entity;

import java.util.Date;

import org.boot.hf.admin.Entity;
import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.IgnoreUpdate;
import org.quincy.rock.core.dao.annotation.JoinTable;
import org.quincy.rock.core.dao.annotation.JoinTables;
import org.quincy.rock.core.dao.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "用户实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_employee", alias = "e", resultMap = "resultMap")
@JoinTables({ @JoinTable(name = "t_ban", alias = "b", onExpr = "e.f_id=b.f_user_id") })
public class Employee extends Entity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6839230938734074806L;

	@ApiModelProperty(value = "代码", required = true, position = 1)
	private String code;

	@ApiModelProperty(value = "名称", required = true, position = 2)
	private String name;

	@ApiModelProperty(value = "是否是管理员", position = 3)
	private Integer admin;

	@ApiModelProperty(value = "员工性别(1-男,2-女)", position = 4)
	private Integer gender;

	@IgnoreUpdate
	@ApiModelProperty(value = "密码", position = 5)
	private String password;

	@ApiModelProperty(value = "入职时间", position = 6)
	private Date joinTime;

	@ApiModelProperty(value = "入职时间", position = 7)
	private Integer phone;

	@ApiModelProperty(value = "工作状态", position = 8)
	private Integer status;

	/*@ApiModelProperty(value = "工作状态", position = 9)
		private byte[] photo;
		
		@ApiModelProperty(value = "工作状态", position = 10)
		private String photoFile;*/

	@ApiModelProperty(value = "返回是否有照片", position = 9)
	@Column(value = "f_photo_file is not null", calculated = true)
	private Boolean hasPhoto;

}
