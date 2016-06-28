package com.cn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.pojo.User;
import com.cn.repository.UserRepository;
import com.cn.service.UserService;

@Service  
@Transactional 
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	public User findByName(String name) {
		// TODO Auto-generated method stub
		System.out.println("findByName" + name);
		return userRepository.findByName(name);
	}

}
