package com.example.demo.entity;

import org.quincy.rock.core.util.StringUtil;

import com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Photo extends Entity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6839895268734074806L;
	private byte[] photo;
	private String photofile;

	public long length() {
		return isEmpty() ? 0 : photo.length;
	}

	public boolean isEmpty() {
		return StringUtil.isBlank(photofile) || photo == null || photo.length == 0;
	}

	public static boolean isEmpty(Photo photo) {
		return photo == null || photo.isEmpty();
	}
}