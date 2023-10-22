package org.boot.hf.admin.service.Impl;

import java.util.Date;

import org.boot.hf.admin.BaseService;
import org.boot.hf.admin.dao.UnlockRequestDao;
import org.boot.hf.admin.entity.UnlockRequest;
import org.boot.hf.admin.service.UnlockRequestService;
import org.quincy.rock.core.util.DateUtil;
import org.springframework.stereotype.Service;

/**
 * <b>UnlockRequestServiceImpl。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author 刘
 * @since 1.0
 */
@Service
public class UnlockRequestServiceImpl extends BaseService<UnlockRequest, UnlockRequestDao> implements UnlockRequestService {

	@Override
	public boolean insert(UnlockRequest entity, boolean ignoreNullValue, String... excluded) {
		Date date = DateUtil.getDateByWord("now");
		entity.setRequestTime(date);
		return super.insert(entity, ignoreNullValue, excluded);
	}


}
