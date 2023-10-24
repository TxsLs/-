package com.example.demo.entity;

import java.util.Date;

import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.Table;

import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_product", alias = "pr")
public class Product extends Entity{
	
	private static final long serialVersionUID=4568465418516512564L;

	@ApiModelProperty(value = "代码", required = false,position = 1)
	private String code;
	
	@ApiModelProperty(value = "商品名称", required = true, position = 2)
	private String name;
	
	@ApiModelProperty(value = "商品描述", required = false,position = 3)
	private String description;
	
	@ApiModelProperty(value = "商品价格", required = true, position = 4)
	private float price;
	
	@ApiModelProperty(value = "商品库存", required = true, position = 5)
	private int stock;
	
	@ApiModelProperty(value = "商品分类编号", required = true, position = 6)
	private Long categoryId;

	@ApiModelProperty(value = "商家编号", required = false,position = 7)
	private Long merchantId;
	
	@ApiModelProperty(value = "创建时间", required = false,position = 8)
	private Date createdTime;
	
}
