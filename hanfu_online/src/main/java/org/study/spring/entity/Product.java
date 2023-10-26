package org.study.spring.entity;

import java.math.BigDecimal;

import org.quincy.rock.core.dao.annotation.Column;
import org.quincy.rock.core.dao.annotation.JoinTable;
import org.quincy.rock.core.dao.annotation.JoinTables;
import org.quincy.rock.core.dao.annotation.Table;
import org.quincy.rock.core.dao.annotation.Temporary;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@ApiModel(description = "商品实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_product", alias = "p")
@JoinTables({ @JoinTable(name = "t_category", alias = "cg", onExpr = "cg.f_id=p.f_category_id"),
	@JoinTable(name = "t_merchant", alias = "m", onExpr = "m.f_id=p.f_merchant_id")}

		)
public class Product extends Entity {
	

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 1234567890123456789L;
	
	
	@ApiModelProperty(value = "名称", required = true, position = 1)
    private String name;

	@ApiModelProperty(value = "分类id", position = 2)
    private String code;
	
    @ApiModelProperty(value = "描述", position = 3)
    private String description;

    @ApiModelProperty(value = "价格", position = 4)
    private BigDecimal price;

    @ApiModelProperty(value = "库存", position = 5)
    private String stock;

    @ApiModelProperty(value = "分类id", position = 6)
    private String categoryId;
    
    @ApiModelProperty(value = "分类名称", position = 7)
	@Column(value = "f_name", tableAlias = "cg", ignoreInsert = true, ignoreUpdate = true)
	private String categoryName;

    
    @ApiModelProperty(value = "商家id", position =8)
    private String merchantId;
    
    @ApiModelProperty(value = "商家名称", position = 9)
	@Column(value = "f_name", tableAlias = "m", ignoreInsert = true, ignoreUpdate = true)
	private String merchantName;


	@ApiModelProperty(value = "返回是否有照片", position = 10)
	@Column(value = "f_photofile is not null", calculated = true)
	private Boolean hasPhoto;
	
    @ApiModelProperty(value = "商家商品状态", position =8)
    private Integer status;

    @ApiModelProperty(value = "商家商品状态", position =8)
    private Integer empStatus;
}
