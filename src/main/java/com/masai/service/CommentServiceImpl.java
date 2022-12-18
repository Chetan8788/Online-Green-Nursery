package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CommentException;
import com.masai.exception.CustomerException;
import com.masai.exception.PlanterException;
import com.masai.model.Comment;
import com.masai.model.Planter;
import com.masai.repository.CommentDao;
import com.masai.repository.CustomerDao;
import com.masai.repository.PlanterDao;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentDao commentDao;
	@Autowired
	CustomerDao customerDao;
	@Autowired
	PlanterDao planterDao;

	@Override
	public Comment viewComment(Integer id) throws CommentException {
		Comment comment = commentDao.findById(id).orElseThrow(() -> new CommentException("Comment not found.."));
		return comment;
	}

	@Override
	public Comment addComment(Comment comment) throws CommentException {
		if (comment == null)
			throw new CommentException("Not found");
//		Customer customer = customerDao.findById(comment.getCustomer().getCustomerId()).orElseThrow(()-> new CommentException("Sorry, user doesnot exit"));
		Planter planter = planterDao.findById(comment.getPlanter().getId())
				.orElseThrow(() -> new CommentException("Planter does not exit"));
		return commentDao.save(comment);
	}

	@Override
	public Comment deleteComment(Integer id) throws CommentException {
		Comment foundComment = commentDao.findById(id).orElseThrow(() -> new CommentException("Comment not found.."));
		return foundComment;
	}

	@Override
	public Comment updateComment(Comment comment) throws CommentException {
		Comment foundcomment = commentDao.findById(comment.getId())
				.orElseThrow(() -> new CommentException("Comment not found.."));
		return commentDao.save(comment);
	}

	@Override
	public List<Comment> viewCommentsByCustomer(Integer customerId) throws CommentException, CustomerException {
		List<Comment> foundcommentList = commentDao.findByCustomer(customerId);
		return foundcommentList;
	}

	@Override
	public List<Comment> viewCommentsOnPlanter(Integer planterId) throws CommentException, PlanterException {
		List<Comment> foundcommentList = commentDao.findByPlanter(planterId);
		return foundcommentList;
	}

	@Override
	public List<Comment> viewRecentComments(LocalDateTime date) throws CommentException {
		List<Comment> foundcommentList = commentDao.findByTimeStampBetween(LocalDateTime.now(), date);
		return foundcommentList;
	}

}
