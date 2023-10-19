package org.boot.hf.admin.service;

import java.util.List;

import org.boot.hf.admin.Service;
import org.boot.hf.admin.entity.Ban;

/**
 * <b>BanService。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
public interface BanService extends Service<Ban> {

	public List<Ban> getBanMes(long userId);

}
