package com.example.demo.entity;

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
	
	private static final long serialVersionUID=45684654185154654L;
	@ApiModelProperty(value = "商品编号", required = true, position = 1)
	private Long id;

	@ApiModelProperty(value = "商品名称", required = true, position = 2)
	private String name;

	@ApiModelProperty(value = "商品描述", required = false,position = 3)
	private String description;

	@ApiModelProperty(value = "商品价格", required = true, position = 4)
	private int price;
	
	@ApiModelProperty(value = "商品库存", required = true, position = 5)
	private int stock;
	
	@ApiModelProperty(value = "商品分类id", required = true, position = 6)
	private Long category_id;
	
	@ApiModelProperty(value = "商品分类名称", required = true, position = 7)
	private String category_name;
	
	@ApiModelProperty(value = "检索字段", required = false,position = 8)
	private Long f_code;
}
