package com.iri.training.repository;

import java.sql.SQLException;
import java.util.List;

import com.iri.training.model.User;


public interface UserRepository {

	User getUserById(Long userId) throws SQLException;
	
	List<User> getUserList() throws SQLException;
	
	User createUser(User user) throws SQLException;
}
