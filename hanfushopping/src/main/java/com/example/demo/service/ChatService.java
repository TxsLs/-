package com.example.demo.service;


import com.example.demo.Service;
import com.example.demo.entity.Chat_record;
import com.example.demo.entity.Photo;

public interface ChatService extends Service<Chat_record>{
	
	public Photo getPhoto(long id);

	public boolean updatePhoto(Photo photo);

	public Chat_record findByCode(String code);
}
