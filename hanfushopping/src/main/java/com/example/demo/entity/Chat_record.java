package com.example.demo.entity;

import org.quincy.rock.core.dao.annotation.Table;
import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "聊天记录实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_chat_record", alias = "c")
public class Chat_record extends Entity{
	private static final long serialVersionUID = 6839230938733674506L;
	@ApiModelProperty(value = "记录编号", required = true, position = 1)
	private Long record_id;

	@ApiModelProperty(value = "顾客id", required = true, position = 2)
	private Long sender_id;

	@ApiModelProperty(value = "客服id", required = true,position = 3)
	private Long receiver_id;

	@ApiModelProperty(value = "消息内容", required = true,position = 4)
	private String message_content;

	@ApiModelProperty(value = "客服类型", required = true, position = 5)
	private Boolean customer_type;

	@ApiModelProperty(value = "收发类型", required = false,position = 6)
	private Boolean send_receive_type;
	
	@ApiModelProperty(value = "检索字段", required = false,position = 7)
	private int f_code;
}
