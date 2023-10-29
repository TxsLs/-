package org.boot.hf.admin.entity;

import org.boot.hf.admin.Entity;
import org.quincy.rock.core.dao.annotation.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>Category。</b>
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
@Table(name = "t_category", alias = "ca")
public class Category extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 8006826910577886900L;

	@ApiModelProperty(value = "分类编号", required = true, position = 1)
	private Long id;

	@ApiModelProperty(value = "分类名称", required = false, position = 2)
	private String name;

}
