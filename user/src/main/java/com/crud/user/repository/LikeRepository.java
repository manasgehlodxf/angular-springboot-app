package com.crud.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.user.entity.Blog;
import com.crud.user.entity.Like;
import com.crud.user.entity.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByBlogAndUser(Blog blog, User user);

}
