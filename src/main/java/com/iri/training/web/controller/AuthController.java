package com.iri.training.web.controller;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iri.training.model.Account;
import com.iri.training.model.User;
import com.iri.training.web.service.AccountService;
import com.iri.training.web.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	Logger logger = Logger.getLogger(AuthController.class);


	@Autowired
	AccountService accountService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = "application/json")
	public ResponseEntity<String> registerAccount(@RequestBody RegistrationWrapper rw) throws SQLException {
		logger.debug("ENTERED registerAccount: " + rw.getAccount() + rw.getUser());

		if ( accountService.verifyNewAccount(rw.getAccount()) &&
			userService.verifyNewUser(rw.getUser())) {

			accountService.createAccount(rw.getAccount());
			userService.addUser(rw.getUser());

			logger.debug("EXITING registerAccount: " + rw.getAccount() + rw.getUser());

			return new ResponseEntity("{\"message\": \"Registration success.\"}", HttpStatus.OK);
		}
		else {
			logger.debug("EXITING registerAccount - Registration failed");

			return new ResponseEntity("{\"message\": \"Registration failed.\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authAccount(@RequestBody Account account) throws SQLException {
		logger.debug("ENTERED authAccount " + account);

		final User user = userService.getUserByUsername(account.getUsername());

		if (account.getUsername() == null || account.getPassword() == null) {
			return new ResponseEntity("{\"message\": \"Insufficient log in data.\"}", HttpStatus.BAD_REQUEST);
		}
		else if (accountService.getAccount(account.getUsername()) == null) {
			return new ResponseEntity("{\"message\": \"Username does not exist.\"}", HttpStatus.NOT_FOUND);
		}
		else if (!accountService.getAccount(account.getUsername()).getPassword().equals(account.getPassword())) {
			return new ResponseEntity("{\"message\": \"Invalid log in details.\"}", HttpStatus.BAD_REQUEST);
		}

		String jwt = Jwts.builder().setIssuer("IRI Training App")
			.setSubject(String.valueOf(accountService.getAccount(account.getUsername()).getAccountId()))
			.setIssuedAt(new Date())
			.claim("name", user.getName())
			.claim("surname", user.getSurname())
			.claim("username", account.getUsername())
			.claim("email", accountService.getAccount(account.getUsername()).getEmail())
			.signWith(SignatureAlgorithm.HS256, "secretkey")
			.compact();

		logger.debug("EXITING authAccount" + account + " Token: "+ jwt);

		return new ResponseEntity<String>(new StringBuilder(200)
			.append("{\"token\": \"")
			.append(jwt)
			.append("\"}")
			.toString(),
			HttpStatus.OK);
	}

	private static class RegistrationWrapper {

		private Account account;
		private User user;

		public Account getAccount() { return account; }

		public void setAccount(Account account) { this.account = account; }

		public User getUser() { return user; }

		public void setUser(User user) { this.user = user; }
	}
}