package com.crud.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.crud.user.service.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{blogId}/like")
    public ResponseEntity<String> likeBlog(@PathVariable Long blogId, @AuthenticationPrincipal UserDetails userDetails) {
        likeService.likeBlog(blogId, userDetails.getUsername());
        return ResponseEntity.ok("Action performed successfully!");
    }
}
