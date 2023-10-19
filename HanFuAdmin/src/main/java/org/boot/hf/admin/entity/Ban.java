package org.boot.hf.admin.entity;

import java.util.Date;

import org.boot.hf.admin.Entity;
import org.quincy.rock.core.dao.annotation.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "封禁表实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_ban", alias = "b")
public class Ban extends Entity {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -1006515762719264021L;

	@ApiModelProperty(value = "封禁者编号", required = true, position = 1)
	private Long userId;

	@ApiModelProperty(value = "封禁理由", required = true, position = 2)
	private String reason;

	@ApiModelProperty(value = "封禁起始时间", position = 3)
	private Date beginTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "封禁结束时间", position = 4)
	private Date endTime;


	@ApiModelProperty(value = "封禁者类型", position = 5)
	private Integer type;

}
