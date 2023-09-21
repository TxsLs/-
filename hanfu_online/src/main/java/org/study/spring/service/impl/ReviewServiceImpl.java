package org.study.spring.service.impl;

import org.springframework.stereotype.Service;
import org.study.spring.BaseService;
import org.study.spring.dao.ReviewDao;
import org.study.spring.entity.Review;
import org.study.spring.service.ReviewService;

@Service
public class ReviewServiceImpl extends BaseService<Review, ReviewDao> implements ReviewService {

}
