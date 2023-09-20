package org.boot.hf.admin.entity;

import org.boot.hf.admin.Entity;
import org.quincy.rock.core.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photo extends Entity {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7109419962019911868L;

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
