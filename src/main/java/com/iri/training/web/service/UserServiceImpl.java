package com.iri.training.web.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iri.training.model.User;
import com.iri.training.repository.UserRepository;

@Service
public  class UserServiceImpl implements UserService {
	Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUserById(Long userId) throws SQLException {
		return userRepository.getUserById(userId);
	}

	@Override
	public User createUser(User user) throws SQLException {
		return userRepository.createUser(user);
	}

	@Override
	public List<User> getUserList() throws SQLException {
		return userRepository.getUserList();
	}
}
