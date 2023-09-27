package com.example.demo.service;


import com.example.demo.Service;
import com.example.demo.entity.Merchant;

public interface MerchantService extends Service<Merchant> {

	public Merchant findByCode(String code);
}
