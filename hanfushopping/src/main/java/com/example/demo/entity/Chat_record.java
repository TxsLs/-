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
@ApiModel(description = "聊天记录实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_merchant", alias = "m")
public class Chat_record extends Entity{

	private static final long serialVersionUID = 145613264L;
	@IgnoreInsert
	@ApiModelProperty(value = "聊天记录编号", required = true, position = 1)
	private String code;
	
	@IgnoreInsert
	@ApiModelProperty(value = "发送者编号", required = false,position = 2)
	private long sender_id;

	@IgnoreInsert
	@ApiModelProperty(value = "接收者编号", required = true,position = 3)
	private long receiver_id;

	@ApiModelProperty(value = "聊天记录", required = true,position = 4)
	private String message_content;

	@ApiModelProperty(value = "发送时间", required = true, position = 5)
	private Date sent_time;

	@IgnoreInsert
	@ApiModelProperty(value = "发送者类型", required = false,position = 6)
	private boolean sender_type;
	
	@IgnoreInsert
	@ApiModelProperty(value = "接受者类型", required = false,position = 7)
	private boolean receiver_type;


}
