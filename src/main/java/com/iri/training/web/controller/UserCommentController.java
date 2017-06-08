
package com.iri.training.web.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iri.training.model.UserComment;
import com.iri.training.web.service.UserCommentService;

@RestController
@RequestMapping("/userComment")
public class UserCommentController{
	Logger logger = Logger.getLogger(UserCommentController.class);
	@Autowired
	UserCommentService userCommentService;
	@RequestMapping(value = "create/{userComment}", method = RequestMethod.GET)
	public void createUserComment(final HttpServletRequest request, @PathVariable("userComment") UserComment userComment)throws SQLException{

		logger.debug("ENTERED createUserComment" + userComment);

		userCommentService.createUserComment(userComment);

		logger.debug("EXITING createUserComment" + userComment);

	}


	@RequestMapping(value = "id/{userId}", method = RequestMethod.GET)
	public UserComment getCommentsByUserId(final HttpServletRequest request, @PathVariable final Long userId) throws SQLException {

		logger.debug("ENTERED getCommentsByUserId" );

		UserComment userComment = userCommentService.getCommentsByUserId(userId);

		logger.debug("EXITING getCommentsByUserId" + userComment);


		return userComment;
	}

}