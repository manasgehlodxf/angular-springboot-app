package com.crud.user.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.crud.user.dto.CommentRequestDTO;
import com.crud.user.dto.CommentResponseDTO;
import com.crud.user.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{blogId}/comment")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long blogId,
                                                          @RequestBody CommentRequestDTO requestDTO,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        requestDTO.setBlogId(blogId);
        CommentResponseDTO commentDTO = commentService.addComment(requestDTO, userDetails.getUsername());
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }
}
