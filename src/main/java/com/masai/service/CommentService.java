package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;

import com.masai.exception.CommentException;
import com.masai.exception.UserException;
import com.masai.exception.PlanterException;
import com.masai.model.Comment;

public interface CommentService {
	Comment viewComment(Integer id) throws CommentException;

	Comment addComment(Comment comment) throws CommentException;

	Comment deleteComment(Integer id) throws CommentException;

	Comment updateComment(Comment comment) throws CommentException;

	List<Comment> viewCommentsByCustomer(Integer customerId) throws CommentException, UserException;

	List<Comment> viewCommentsOnPlanter(Integer planterId) throws CommentException, PlanterException;

	List<Comment> viewRecentComments(LocalDateTime date) throws CommentException;
}
