package com.crud.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.user.entity.Task;
import com.crud.user.enums.TaskStatus;
import com.crud.user.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return taskService.getUserTasks(username);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return taskService.createTask(task, username);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        taskService.deleteTask(taskId, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskId}/status")
    public Task updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return taskService.updateTaskStatus(taskId, status, username);
    }
}
