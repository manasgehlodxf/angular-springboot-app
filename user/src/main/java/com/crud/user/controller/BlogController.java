package com.crud.user.controller;


import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.crud.user.dto.BlogDetailsDTO;
import com.crud.user.dto.BlogRequestDTO;
import com.crud.user.dto.BlogResponseDTO;
import com.crud.user.service.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<BlogResponseDTO> createBlog(@RequestBody BlogRequestDTO requestDTO, Principal principal) {
        String email = principal.getName();
        BlogResponseDTO responseDTO = blogService.createBlog(requestDTO, email);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogs() {
        List<BlogResponseDTO> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDetailsDTO> getBlogById(@PathVariable Long id) {
        BlogDetailsDTO blogDetailsDTO = blogService.getBlogById(id);
        return ResponseEntity.ok(blogDetailsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id, Principal principal) {
        blogService.deleteBlog(id, principal.getName());
        return ResponseEntity.ok("Blog deleted successfully!");
    }
}
