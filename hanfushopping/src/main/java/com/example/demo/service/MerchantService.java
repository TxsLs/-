package com.example.demo.service;

import java.util.Map;
import org.quincy.rock.core.dao.sql.Sort;
import org.quincy.rock.core.vo.PageSet;
import com.example.demo.Service;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.Photo;

public interface MerchantService extends Service<Merchant> {
	public Merchant findByCode(String code);
}
