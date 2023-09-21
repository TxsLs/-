package org.study.spring.entity;

import org.quincy.rock.core.dao.annotation.IgnoreInsert;
import org.quincy.rock.core.dao.annotation.IgnoreUpdate;
import org.quincy.rock.core.dao.annotation.Table;
import org.quincy.rock.core.dao.annotation.Temporary;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@ApiModel(description = "用户实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "product", alias = "p")
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
    private String price;

    @ApiModelProperty(value = "库存", position = 5)
    private String stock;

    @ApiModelProperty(value = "分类id", position = 6)
    private String categoryId;
    
    @ApiModelProperty(value = "商家id", position = 7)
    private String merchantId;
    
    @ApiModelProperty(value = "附件名称", position = 8)
    @IgnoreInsert
	@IgnoreUpdate
	private String additionalName;

	@ApiModelProperty(value = "附件文件url", position = 9)
	@IgnoreInsert
	@IgnoreUpdate
	private String additionalFile;
	
    @Temporary
	@ApiModelProperty(value = "返回是否有照片", position = 10)
	private Boolean hasPhoto;

}
