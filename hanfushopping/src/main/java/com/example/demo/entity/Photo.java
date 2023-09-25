package com.example.demo.entity;

import org.quincy.rock.core.dao.annotation.Table;
import org.quincy.rock.core.util.StringUtil;

import com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "照片实体(在执行更新操作时采取动态更新策略，如果属性值为空，则忽略该属性)")
@Table(name = "t_order_table", alias = "ph")
public class Photo extends Entity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6839895268734074806L;
	private byte[] photo;
	private String photoFile;

	public long length() {
		return isEmpty() ? 0 : photo.length;
	}

	public boolean isEmpty() {
		return StringUtil.isBlank(photoFile) || photo == null || photo.length == 0;
	}

	public static boolean isEmpty(Photo photo) {
		return photo == null || photo.isEmpty();
	}
}