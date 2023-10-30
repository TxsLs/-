package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Null;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.JoinTable;
import org.quincy.rock.core.dao.annotation.JoinTables;
import org.quincy.rock.core.dao.annotation.Table;

import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_product", alias = "p")
@JoinTables({ @JoinTable(name = "t_category", alias = "cg", onExpr = "cg.f_id=p.f_category_id"),
		@JoinTable(name = "t_merchant", alias = "m", onExpr = "m.f_id=p.f_merchant_id") })
public class Product extends Entity {

	

	private static final long serialVersionUID = 4568465418516512564L;

	@ApiModelProperty(value = "代码", required = false, position = 1)
	private String code;

	@ApiModelProperty(value = "商品名称", required = true, position = 2)
	private String name;

	@ApiModelProperty(value = "商品描述", required = false, position = 3)
	private String description;

	@ApiModelProperty(value = "商品价格", required = true, position = 4)
	private BigDecimal price;

	@ApiModelProperty(value = "商品库存", required = true, position = 5)
	private Integer stock;

	@ApiModelProperty(value = "商品分类编号", required = true, position = 6)
	private Long categoryId;

	@ApiModelProperty(value = "商家编号", required = false, position = 7)
	private Long merchantId;

	//	@ApiModelProperty(value = "创建时间", required = false,position = 8)
	//	private Date createdTime;

	@ApiModelProperty(value = "返回是否有照片", position = 8)
	@Column(value = "f_photofile is not null", calculated = true)
	private Boolean hasPhoto;

	@ApiModelProperty(value = "分类名称", position = 9)
	@Column(value = "f_name", tableAlias = "cg", ignoreInsert = true, ignoreUpdate = true)
	private String categoryName;

	@ApiModelProperty(value = "商品状态", required = true, position = 10)
	private Integer status;
	
	@ApiModelProperty(value = "商品状态", required = true, position = 11)
	private Integer empStatus;
	
}
