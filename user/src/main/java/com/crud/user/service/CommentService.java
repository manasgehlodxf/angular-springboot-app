package com.crud.user.service;

import org.springframework.stereotype.Service;

import com.crud.user.dto.CommentMapper;
import com.crud.user.dto.CommentRequestDTO;
import com.crud.user.dto.CommentResponseDTO;
import com.crud.user.entity.Blog;
import com.crud.user.entity.Comment;
import com.crud.user.entity.User;
import com.crud.user.repository.BlogRepository;
import com.crud.user.repository.CommentRepository;
import com.crud.user.repository.UserRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository,
                           UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponseDTO 
    addComment(CommentRequestDTO requestDTO, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Blog blog = blogRepository.findById(requestDTO.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        Comment comment = new Comment();
        comment.setContent(requestDTO.getContent());
        comment.setUser(user);
        comment.setBlog(blog);

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponseDTO(savedComment);
    }
}
