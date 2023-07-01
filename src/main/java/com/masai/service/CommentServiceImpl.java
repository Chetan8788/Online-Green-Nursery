package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	CommentDao commentDao;

	@Autowired
	UserDao customerDao;

	@Autowired
	PlanterDao planterDao;

	@Override
	public Comment viewComment(Integer id) throws CommentException {
		logger.info("Fetching comment by ID: {}", id);
		Comment comment = commentDao.findById(id)
				.orElseThrow(() -> new CommentException("Comment not found with ID: " + id));
		return comment;
	}

	@Override
	public Comment addComment(Comment comment) throws CommentException {
		logger.info("Adding comment");

		if (comment == null)
			throw new CommentException("Comment cannot be null");

		Planter planter = planterDao.findById(comment.getPlanter().getId())
				.orElseThrow(() -> new CommentException("Planter does not exist"));

		if (comment.getRating() != null) {
			Long nRatings = planter.getNumberOfRatings() + 1;
			Float ratings = planter.getRating();
			Float newRatings = ((ratings * nRatings) + comment.getRating()) / nRatings;

			planter.setNumberOfRatings(nRatings);
			planter.setRating(newRatings);
			planterDao.save(planter);
		}

		Comment savedComment = commentDao.save(comment);
		logger.info("Comment added successfully");
		return savedComment;
	}

	@Override
	public Comment deleteComment(Integer id) throws CommentException {
		logger.info("Deleting comment with ID: {}", id);
		Comment foundComment = commentDao.findById(id)
				.orElseThrow(() -> new CommentException("Comment not found with ID: " + id));

		commentDao.delete(foundComment);
		logger.info("Comment deleted successfully");
		return foundComment;
	}

	@Override
	public Comment updateComment(Comment comment) throws CommentException {
		logger.info("Updating comment with ID: {}", comment.getId());
		Comment foundcomment = commentDao.findById(comment.getId())
				.orElseThrow(() -> new CommentException("Comment not found with ID: " + comment.getId()));

		Planter planter = planterDao.findById(comment.getPlanter().getId())
				.orElseThrow(() -> new CommentException("Planter does not exist"));

		if (comment.getRating() != null) {
			Long nRatings = planter.getNumberOfRatings() + 1;
			Float ratings = planter.getRating();
			Float newRatings = ((ratings * nRatings) + comment.getRating()) / nRatings;

			planter.setNumberOfRatings(nRatings);
			planter.setRating(newRatings);
			planterDao.save(planter);
		}

		Comment updatedComment = commentDao.save(comment);
		logger.info("Comment updated successfully");
		return updatedComment;
	}

	@Override
	public List<Comment> viewCommentsByUser(Integer userId) throws CommentException, UserException {
		logger.info("Fetching comments by User ID: {}", userId);

		List<Comment> foundcommentList = commentDao.findByUser(userId);
		return foundcommentList;
	}

	@Override
	public List<Comment> viewCommentsOnPlanter(Integer planterId) throws CommentException, PlanterException {
		logger.info("Fetching comments on Planter ID: {}", planterId);

		List<Comment> foundcommentList = commentDao.findByPlanter(planterId);
		return foundcommentList;
	}

	@Override
	public List<Comment> viewRecentComments(LocalDateTime date) throws CommentException {
		logger.info("Fetching recent comments since: {}", date);

		List<Comment> foundcommentList = commentDao.findByTimeStampBetween(LocalDateTime.now(), date);
		return foundcommentList;
	}
}
