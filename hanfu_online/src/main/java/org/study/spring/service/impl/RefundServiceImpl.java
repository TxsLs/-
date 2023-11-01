package org.study.spring.service.impl;

import java.util.Date;
import java.util.Map;

import org.quincy.rock.core.dao.sql.Predicate;
import org.quincy.rock.core.util.DateUtil;
import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.RefundDao;
import org.study.spring.entity.Refund;
import org.study.spring.service.RefundService;


@Service
public class RefundServiceImpl extends BaseService<Refund, RefundDao> implements RefundService {

	@Override
	public boolean updateMap(Map<String, Object> voMap, Predicate where) {
		Date date = DateUtil.getDateByWord("now");
		voMap.put("processingTime", date);
		return super.updateMap(voMap, where);
	}

}
