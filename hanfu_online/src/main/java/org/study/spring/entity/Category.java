package org.study.spring.entity;



import org.quincy.rock.core.dao.annotation.Table;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品分类实体")
@Table(name = "t_category", alias = "cg")
public class Category extends Entity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7748107279747664901L;



	@ApiModelProperty(value = "名称", required = true, position = 1)
	private String name;

	@ApiModelProperty(value = "父类id", position = 2)
	private String parentId;
	
	@ApiModelProperty(value = "代码", required = true, position = 3)
	private String code;
}
