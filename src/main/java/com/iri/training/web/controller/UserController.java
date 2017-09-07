package com.iri.training.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iri.training.model.User;
import com.iri.training.web.service.UserService;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	Logger logger = Logger.getLogger(UserController.class);


	@Autowired
	UserService userService;

	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = "application/json")
	public ResponseEntity<String> editUser(@RequestBody User user) throws SQLException {

		logger.debug("ENTERED editUser: " + user );
		if ( userService.verifyNewUser(user)) {
			userService.updateUser(user);

			logger.debug("EXITING editUser: " + user);

			return new ResponseEntity("{\"message\": \"Update success.\"}", HttpStatus.OK);
		}else{
			return new ResponseEntity("{\"message\": \"Update failed.\"}", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<User>> getAllUsers() throws SQLException {

		logger.debug("ENTERED getAllUsers");

		ArrayList<User> users = (ArrayList) userService.getUserList();

		logger.debug("EXITING getAllUsers");

		if (users != null) {
			return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
		}

		return new ResponseEntity<ArrayList<User>>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) throws SQLException {


		logger.debug("ENTERED getUserByUsername: " + username);

		User user = userService.getUserByUsername(username);

		logger.debug("EXITING getUserByUsername " + user);

		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/uid/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) throws SQLException {

		logger.debug("ENTERED getUserById: " + userId);

		User user = userService.getUserById(userId);

		logger.debug("EXITING getUserById " + user);

		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

}
