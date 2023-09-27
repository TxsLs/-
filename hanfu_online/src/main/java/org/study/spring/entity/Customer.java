package org.study.spring.entity;

import java.sql.Date;
import java.util.List;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.IgnoreInsertUpdate;
import org.quincy.rock.core.dao.annotation.IgnoreUpdate;
import org.quincy.rock.core.dao.annotation.Table;
import org.quincy.rock.core.dao.annotation.Temporary;
import org.springframework.security.core.GrantedAuthority;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "顾客实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_customer", alias = "u")
public class Customer extends Entity {


	
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1097688549172692010L;

	@ApiModelProperty(value = "编号", position = 1)
    private String code;
	
	@ApiModelProperty(value = "名称", required = true, position = 2)
    private String name;

    @ApiModelProperty(value = "密码", position = 3)
    private String password;

    @ApiModelProperty(value = "邮箱", position = 4)
    private String email;

    @ApiModelProperty(value = "电话", position = 5)
    private String phone;

    @ApiModelProperty(value = "性别", position = 6)
    private Integer gender;

    @ApiModelProperty(value = "创建时间", position = 7)
    @IgnoreInsertUpdate
    private Date createTime;

	
	@ApiModelProperty(value = "返回是否有照片", position = 8)
    @Column(value = "f_photo_file is not null", calculated = true)
	private Boolean hasPhoto;
	
    @ApiModelProperty(value = "是否被封禁", position = 9)
    private byte isviolate;

}
