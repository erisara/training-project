package com.iri.training.web.service;

import java.sql.SQLException;
import java.util.List;

import com.iri.training.model.User;


public interface UserService {

	User getUserByUsername(String username) throws SQLException;

	User getUserById(Long userId) throws SQLException;

	List<User> getUserList() throws SQLException;

	long addUserAndGetGeneratedId(User user) throws SQLException;

	boolean verifyNewUser(User user) throws SQLException;

	void updateUser(User user) throws SQLException;
}
