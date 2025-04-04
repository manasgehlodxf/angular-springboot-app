package com.crud.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.user.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{

}
