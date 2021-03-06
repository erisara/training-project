package com.iri.training.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iri.training.enums.SubjectType;
import com.iri.training.model.Post;
import com.iri.training.web.service.VerificationService;
import com.iri.training.web.service.CommentService;
import com.iri.training.web.service.PostService;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/post")
public final class PostController {

	private static final Logger logger = Logger.getLogger(PostController.class);

	@Autowired
	VerificationService verificationService;
	@Autowired
	CommentService commentService;
	@Autowired
	PostService postService;

	@RequestMapping(value = "/{postId}", method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Post> getPostById(
			@PathVariable("postId") final long postId,
			@RequestParam("getComments") final Optional<Boolean> getComments) throws SQLException {

		logger.debug("ENTERED getPostById for postId: " + postId +
			" with getComments=" + getComments);;

		final Post post = postService.getPostById(postId, getComments.orElse(false));

		logger.debug("EXITING getPostById with post: " + post);

		if (post != null) {
			return new ResponseEntity<Post>(post, HttpStatus.OK);
		}

		return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/subject/{subjectType}/{subjectId}", method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<List<Post>>  getPostsBySubject(
			@PathVariable("subjectType") final SubjectType subjectType,
			@PathVariable("subjectId") final long subjectId,
			@RequestParam("getComments") final Optional<Boolean> getComments) throws SQLException {

		logger.debug("ENTERED getPostsBySubject for subjectType: " + subjectType +
			" with subjectId: " + subjectId +
			" with getComments=" + getComments);

		final List<Post> posts = new ArrayList<Post>(
			postService.getPostsBySubject(subjectType, subjectId, getComments.orElse(false)));

		logger.debug("EXITING getPostsBySubject for subjectType: " + subjectType +
			" with subjectId: " + subjectId +
			" with getComments=" + getComments);

		if (posts != null) {
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		}

		return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/poster/{posterId}", method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<List<Post>> getPostsByPoster(
				@PathVariable("posterId") final long posterId,
				@RequestParam("getComments") final Optional<Boolean> getComments) throws SQLException {

		logger.debug("ENTERED getPostByPoster for posterId: " + posterId +
			" with getComments=" + getComments);

		final List<Post> posts = new ArrayList<Post>(
			postService.getPostsByPoster(posterId, getComments.orElse(false)));

		logger.debug("EXITING getPostByPoster for posterId: " + posterId +
			" with getComments=" + getComments);

		if (posts != null) {
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		}

		return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity addPost(@RequestHeader(value="Authorization") final String authHeader,
		@RequestBody final Post post) throws SQLException {

		logger.debug("ENTERED addPost for post: " + post);

		if (verificationService.verifyAddRights(post, authHeader) &&
			verificationService.verifyPostable(post)) {

			final Post postFromDB = postService.addPost(post);

			logger.debug("EXITING addPost for post: " + post + ". Post added successfully.");

			return new ResponseEntity(postFromDB, HttpStatus.OK);
		}
		else {
			logger.debug("EXITING addPost for post: " + post + ". Posting failed.");

			return new ResponseEntity("{\"message\": \"Posting failed.\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{postId}/delete", method = RequestMethod.DELETE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<String> deletePost(@RequestHeader(value="Authorization") final String authHeader,
				@PathVariable("postId") final long postId) throws SQLException {

		logger.debug("ENTERED deletePost for postId: " + postId);

		if (verificationService.verifyDeleteRights(SubjectType.POST, postId, authHeader)) {
			postService.deletePost(postId);

			logger.debug("EXITING deletePost for postId: " + postId + ". Delete success.");

			return new ResponseEntity("{\"message\": \"Post deleted successfully.\"}", HttpStatus.OK);
		}
		else {
			logger.debug("EXITING deletePost for postId: " + postId + ". Delete failed.");

			return new ResponseEntity("{\"message\": \"Post deletion failed.\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity editPost(@RequestHeader(value="Authorization") final String authHeader,
				@RequestBody final Post post) throws SQLException {

		logger.debug("ENTERED editPost for post: " + post);

		if (verificationService.verifyEditRights(post, authHeader) &&
			verificationService.verifyPostable(post)) {

			final Post postFromDB = postService.editPost(post);

			logger.debug("EXITING editPost for post: " + post + ". Post edited successfully.");

			return new ResponseEntity(postFromDB, HttpStatus.OK);
		}
		else {
			logger.debug("EXITING editPost for post: " + post + ". Post editing failed.");

			return new ResponseEntity("{\"message\": \"Post editing failed.\"}", HttpStatus.BAD_REQUEST);
		}
	}
}
