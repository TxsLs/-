package com.example.demo.service.impl;

import java.util.Date;

import org.quincy.rock.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.BaseService;
import com.example.demo.dao.ChatDao;
import com.example.demo.entity.Chat_record;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.Photo;
import com.example.demo.service.ChatService;


@Service
public class ChatServiceImpl extends BaseService<Chat_record, ChatDao> implements ChatService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Chat_record findByCode(String code) {
		return this.dao().findByName("code", code);
	}
	
	@Override
	public Photo getPhoto(long id) {
		return this.dao().getPhoto(id);
	}

	@Override
	@Transactional
	public boolean updatePhoto(Photo photo) {
		return dao().updatePhoto(photo) > 0;
	}
	
	@Override
	public boolean insert(Chat_record entity, boolean ignoreNullValue, String... excluded) {

		Date date=DateUtil.getDateByWord("now");
		entity.setSent_time(date);
		return super.insert(entity, ignoreNullValue, excluded);
	}
}
