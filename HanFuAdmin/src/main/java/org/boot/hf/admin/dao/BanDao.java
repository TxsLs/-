package org.boot.hf.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.boot.hf.admin.Dao;
import org.boot.hf.admin.entity.Ban;

/**
 * <b>BanDao。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Mapper
public interface BanDao extends Dao<Ban> {

	public List<Ban> getBanMes(long userId);

}
