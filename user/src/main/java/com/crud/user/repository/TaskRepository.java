package com.crud.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.user.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	 List<Task> findByCreatedBy(String createdBy);

}
