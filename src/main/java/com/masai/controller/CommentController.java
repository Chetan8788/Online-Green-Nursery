package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.auth.Authorization;
import com.masai.auth.exception.AuthException;
import com.masai.model.Comment;
import com.masai.service.CommentService;
import com.masai.service.CustomerService;
import com.masai.service.PlanterService;

@RestController
@RequestMapping(value = "comments")
public class CommentController {
	@Autowired
	CommentService commentService;
	@Autowired
	Authorization authorization;
	@Autowired
	CustomerService customerService;
	@Autowired
	PlanterService planterService;

	@GetMapping(value = "{id}")
	public ResponseEntity<Comment> viewCommentById(@PathVariable Integer id) {

		return new ResponseEntity<Comment>(commentService.viewComment(id), HttpStatus.OK);
	}

	@GetMapping(value = "/customers/{customerId}")
	public ResponseEntity<List<Comment>> viewCommentsByCustomer(@PathVariable Integer customerId) {

		return new ResponseEntity<List<Comment>>(commentService.viewCommentsByCustomer(customerId), HttpStatus.OK);
	}

	@GetMapping(value = "/planters/{planterId}")
	public ResponseEntity<List<Comment>> viewCommentsOnPlanter(@PathVariable Integer planterId) {

		return new ResponseEntity<List<Comment>>(commentService.viewCommentsOnPlanter(planterId), HttpStatus.OK);
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Comment>> viewRecentComments(@RequestParam(required = false) LocalDateTime date) {
		if (date == null)
			date = LocalDateTime.now().minusDays(10);
		return new ResponseEntity<List<Comment>>(commentService.viewRecentComments(date), HttpStatus.OK);
	}

	@PostMapping(value = "")
	public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment, @RequestHeader String token) {
		return new ResponseEntity<Comment>(commentService.addComment(comment), HttpStatus.CREATED);

	}

	@PutMapping(value = "")
	public ResponseEntity<Comment> updateComment(@Valid @RequestBody Comment comment, @RequestHeader String token) {
		Integer id = authorization.isAuthorized(token, "customer");
		if (id != comment.getCustomer().getCustomerId())
			throw new AuthException("Sorry you can not update others post");
		return new ResponseEntity<Comment>(commentService.updateComment(comment), HttpStatus.CREATED);

	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Comment> deleteComment(@PathVariable Integer id, @RequestHeader String token) {
		Integer cid = authorization.isAuthorized(token, "customer");
		if (commentService.viewComment(id).getCustomer().getCustomerId() != cid)
			throw new AuthException("Sorry you can not update others post");
		return new ResponseEntity<Comment>(commentService.deleteComment(id), HttpStatus.CREATED);

	}

}
