package com.crud.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crud.user.entity.Blog;
import com.crud.user.entity.Like;
import com.crud.user.entity.User;
import com.crud.user.repository.BlogRepository;
import com.crud.user.repository.LikeRepository;
import com.crud.user.repository.UserRepository;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, BlogRepository blogRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public void likeBlog(Long blogId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        Optional<Like> existingLike = likeRepository.findByBlogAndUser(blog, user);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());  // Unlike if already liked
        } else {
            Like like = new Like();
            like.setBlog(blog);
            like.setUser(user);
            likeRepository.save(like);
        }
    }
}
