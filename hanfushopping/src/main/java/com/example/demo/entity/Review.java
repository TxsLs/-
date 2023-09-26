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
@ApiModel(description = "订单实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_merchant", alias = "m")
public class Review extends Entity{
	private static final long serialVersionUID = 683923032174806L;
	@ApiModelProperty(value = "账号", required = true, position = 1)
	private String code;
	
	@IgnoreInsert
	@ApiModelProperty(value = "顾客编号", required = false,position = 2)
	private long customer_id;

	@IgnoreInsert
	@ApiModelProperty(value = "创建时间", required = true,position = 3)
	private long product_id;

	@IgnoreInsert
	@ApiModelProperty(value = "商家描述", required = true,position = 4)
	private String introduction;

	@IgnoreInsert
	@ApiModelProperty(value = "评价内容", required = true, position = 5)
	private String review_content;

	@IgnoreInsert
	@ApiModelProperty(value = "评分", required = false,position = 6)
	private float rating;
	
	@IgnoreInsert
	@ApiModelProperty(value = "创建时间", required = false,position = 7)
	private Date creat_time;

}
