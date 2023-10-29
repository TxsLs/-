package org.boot.hf.admin.entity;

import org.boot.hf.admin.Entity;
import org.quincy.rock.core.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * <b>PhotoMerchant。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Getter
@Setter

public class PhotoShops extends Entity{

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -7834924534987709284L;

	private byte[] photo;
	private String photofile;

	public long length() {
		return isEmpty() ? 0 : photo.length;
	}

	public boolean isEmpty() {
		return StringUtil.isBlank(photofile) || photo == null || photo.length == 0;
	}

	public static boolean isEmpty(PhotoShops photo) {
		return photo == null || photo.isEmpty();
	}
	
	
}
