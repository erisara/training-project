package com.iri.training.web.service;

import java.sql.SQLException;

import com.iri.training.model.UserComment;

public interface UserCommentService {

	 UserComment getUserCommentById(Long userId) throws SQLException ;



}