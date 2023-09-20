package org.study.spring.entity;

import java.sql.Date;

import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.IgnoreUpdate;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <b>评价实体</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author Nymphet
 * @since 1.0
 */
public class Review extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -443057957024622999L;


	@ApiModelProperty(value = "评价编号", position = 1)
    private String code;
	
	@ApiModelProperty(value = "顾客编号", position = 2)
    private String customerId;
	
	@ApiModelProperty(value = "商品编号", position = 3)
    private String productId;
	
	@ApiModelProperty(value = "评价内容", position = 4)
    private String reviewContent;
	
	@ApiModelProperty(value = "评分", position = 5)
    private String rating;
	
	@ApiModelProperty(value = "评价时间", position = 6)
    @IgnoreInsert
	@IgnoreUpdate
    private Date createdTime;
	
	
	
	
	
}
