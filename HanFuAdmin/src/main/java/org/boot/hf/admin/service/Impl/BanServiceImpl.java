package org.boot.hf.admin.service.Impl;

import java.util.Date;
import java.util.List;

import org.boot.hf.admin.BaseService;
import org.boot.hf.admin.dao.BanDao;
import org.boot.hf.admin.entity.Ban;
import org.boot.hf.admin.service.BanService;
import org.quincy.rock.core.util.DateUtil;
import org.springframework.stereotype.Service;

/**
 * <b>BanServiceImpl。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Service
public class BanServiceImpl extends BaseService<Ban, BanDao> implements BanService {

	@Override
	public boolean insert(Ban entity, boolean ignoreNullValue, String... excluded) {
		Date date = DateUtil.getDateByWord("now");
		entity.setBeginTime(date);
		return super.insert(entity, ignoreNullValue, excluded);
	}

	@Override
	public List<Ban> getBanMes(long userId,int type) {
		return this.dao().getBanMes(userId,type);
	}

}
