package org.study.spring.entity;

import org.quincy.rock.core.dao.annotation.Table;
import org.study.spring.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>聊天记录实体。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author Nymphet
 * @since 1.0
 */


@Getter
@Setter
@ApiModel(description = "聊天记录实体")
@Table(name = "t_chat_record", alias = "cr")
public class ChatRecord extends Entity {
	
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6839230938734074806L;
	
	@ApiModelProperty(value = "编号", position = 0)
    private String code;
	
	@ApiModelProperty(value = "发送者id", required = true, position = 1)
    private String senderId;

    @ApiModelProperty(value = "接收者id", position = 2)
    private String receiverId;

    @ApiModelProperty(value = "消息内容", position = 3)
    private String messageContent;

    @ApiModelProperty(value = "发送时间", position = 4)
    private String sentTime;

    @ApiModelProperty(value = "发送者类型", position = 5)
    private String senderType;
    
    @ApiModelProperty(value = "接收者类型", position = 6)
	private String receiverType;
    
    @ApiModelProperty(value = "是否已读", position = 7)
	private String status;


}
