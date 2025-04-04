package com.crud.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.user.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	 List<Comment> findByBlogId(Long blogId);
}
