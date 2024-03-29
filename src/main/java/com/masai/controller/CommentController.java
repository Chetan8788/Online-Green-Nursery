package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.CommentException;
import com.masai.model.Comment;
import com.masai.service.CommentService;
import com.masai.service.UserHelper;

/**
 * The CommentController class handles CRUD operations for comments. It provides
 * methods for fetching comments by ID, user ID, planter ID, fetching recent
 * comments, adding a new comment, updating a comment, and deleting a comment.
 */
@RestController
@RequestMapping(value = "comments")
@CrossOrigin("*")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserHelper userHelper;

	/**
	 * Retrieves a comment by its ID.
	 *
	 * @param id The ID of the comment to retrieve.
	 * @return ResponseEntity containing the fetched Comment object.
	 */
	@GetMapping(value = "{id}")
	public ResponseEntity<Comment> viewCommentById(@PathVariable Integer id) {
		logger.info("Fetching comment by ID: {}", id);
		return new ResponseEntity<Comment>(commentService.viewComment(id), HttpStatus.OK);
	}

	/**
	 * Retrieves all comments associated with a specific user.
	 *
	 * @param userId The ID of the user.
	 * @return ResponseEntity containing a list of Comment objects.
	 */
	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<List<Comment>> viewCommentsByUserId(@PathVariable("userId") Integer userId) {
		logger.info("Fetching comments by User ID: {}", userId);
		return new ResponseEntity<List<Comment>>(commentService.viewCommentsByUser(userId), HttpStatus.OK);
	}

	/**
	 * Retrieves all comments associated with a specific planter.
	 *
	 * @param planterId The ID of the planter.
	 * @return ResponseEntity containing a list of Comment objects.
	 */
	@GetMapping(value = "/planters/{planterId}")
	public ResponseEntity<List<Comment>> viewCommentsByPlanterId(@PathVariable Integer planterId) {
		logger.info("Fetching comments by Planter ID: {}", planterId);
		return new ResponseEntity<List<Comment>>(commentService.viewCommentsOnPlanter(planterId), HttpStatus.OK);
	}

	/**
	 * Retrieves recent comments since a specified date.
	 *
	 * @param date The date from which to fetch recent comments.
	 * @return ResponseEntity containing a list of Comment objects.
	 */
	@GetMapping(value = "")
	public ResponseEntity<List<Comment>> viewRecentComments(@RequestParam(required = false) LocalDateTime date) {
		if (date == null)
			date = LocalDateTime.now().minusDays(10);

		logger.info("Fetching recent comments since: {}", date);
		return new ResponseEntity<List<Comment>>(commentService.viewRecentComments(date), HttpStatus.OK);
	}

	/**
	 * Adds a new comment.
	 *
	 * @param comment The Comment object to add.
	 * @return ResponseEntity containing the created Comment object.
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "")
	public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment) {
		logger.info("Adding a new comment");
		return new ResponseEntity<Comment>(commentService.addComment(comment), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing comment.
	 *
	 * @param comment The updated Comment object.
	 * @return ResponseEntity containing the updated Comment object.
	 */
	@PutMapping(value = "")
	public ResponseEntity<Comment> updateComment(@Valid @RequestBody Comment comment) {
		logger.info("Updating comment with ID: {}", comment.getId());

		String loggedInEmail = userHelper.getLoggedInEmail();

		if (!loggedInEmail.equals(comment.getUser().getEmail()))
			throw new CommentException("Sorry, you cannot update others' posts");

		logger.info("Comment updated successfully");
		return new ResponseEntity<Comment>(commentService.updateComment(comment), HttpStatus.CREATED);
	}

	/**
	 * Deletes a comment by its ID.
	 *
	 * @param commentId The ID of the comment to delete.
	 * @return ResponseEntity containing the deleted Comment object.
	 */
	@DeleteMapping(value = "{commentId}")
	public ResponseEntity<Comment> deleteComment(@PathVariable("commentId") Integer commentId) {
		logger.info("Deleting comment with ID: {}", commentId);

		String loggedInEmail = userHelper.getLoggedInEmail();

		if (!loggedInEmail.equals(commentService.viewComment(commentId).getUser().getEmail()))
			throw new CommentException("Sorry, you cannot delete others' posts");

		logger.info("Comment deleted successfully");
		return new ResponseEntity<Comment>(commentService.deleteComment(commentId), HttpStatus.CREATED);
	}
}
