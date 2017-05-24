package com.iri.training.repository;

import java.sql.SQLException;

import com.iri.training.model.UserComment;

public interface UserCommentRepository {
	UserComment getUserCommentById(Long userId) throws SQLException, ClassNotFoundException;
	UserComment createUserComment(UserComment userComment) throws SQLException, ClassNotFoundException;
}
