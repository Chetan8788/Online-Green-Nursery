package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CommentException;
import com.masai.exception.UserException;
import com.masai.exception.PlanterException;
import com.masai.model.Comment;
import com.masai.model.Planter;
import com.masai.repository.CommentDao;
import com.masai.repository.UserDao;
import com.masai.repository.PlanterDao;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentDao commentDao;
	@Autowired
	UserDao customerDao;
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
		if (comment.getRating() != null) {
			Long nRatings = planter.getNumberOfRatings() + 1;
			Float ratings = planter.getRating();
			Float newRatings = ((ratings * nRatings) + comment.getRating()) / nRatings;

		}
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
		Planter planter = planterDao.findById(comment.getPlanter().getId())
				.orElseThrow(() -> new CommentException("Planter does not exit"));
		if (comment.getRating() != null) {
			Long nRatings = planter.getNumberOfRatings() + 1;
			Float ratings = planter.getRating();
			Float newRatings = ((ratings * nRatings) + comment.getRating()) / nRatings;

		}
		return commentDao.save(comment);
	}

	@Override
	public List<Comment> viewCommentsByUser(Integer userId) throws CommentException, UserException {
		List<Comment> foundcommentList = commentDao.findByUser(userId);
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
