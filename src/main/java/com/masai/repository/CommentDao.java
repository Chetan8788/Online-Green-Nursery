package com.masai.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Comment;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {

	List<Comment> findByCustomer(Integer customerId);

	List<Comment> findByPlanter(Integer planterId);

	List<Comment> findByTimeStampBetween(LocalDateTime now, LocalDateTime date);

}
