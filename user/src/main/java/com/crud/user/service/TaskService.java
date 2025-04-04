package com.crud.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.crud.user.entity.Task;
import com.crud.user.enums.TaskStatus;
import com.crud.user.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getUserTasks(String username) {
        return taskRepository.findByCreatedBy(username);
    }

    public Task createTask(Task task, String username) {
        try {
        	  task.setCreatedBy(username); // Set createdBy to the logged-in user
              return taskRepository.save(task);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Catch block ");
			e.getMessage();
		}
		return task;
    }

    public void deleteTask(Long taskId, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getCreatedBy().equals(username)) {
            taskRepository.delete(task);
        } else {
            throw new AccessDeniedException("Unauthorized to delete this task.");
        }
    }

    public Task updateTaskStatus(Long taskId, TaskStatus status, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.getCreatedBy().equals(username)) {
            task.setStatus(status);
            return taskRepository.save(task);
        } else {
            throw new AccessDeniedException("Unauthorized to update this task.");
        }
    }
}
