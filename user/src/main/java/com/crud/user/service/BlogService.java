package com.crud.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crud.user.dto.BlogDetailsDTO;
import com.crud.user.dto.BlogMapper;
import com.crud.user.dto.BlogRequestDTO;
import com.crud.user.dto.BlogResponseDTO;
import com.crud.user.entity.Blog;
import com.crud.user.entity.Comment;
import com.crud.user.entity.User;
import com.crud.user.repository.BlogRepository;
import com.crud.user.repository.CommentRepository;
import com.crud.user.repository.UserRepository;

@Service
public class BlogService {

	private final BlogRepository blogRepository;
	private final UserRepository userRepository;
	private final BlogMapper blogMapper;
	private final CommentRepository commentRepository;

	public BlogService(BlogRepository blogRepository, UserRepository userRepository, BlogMapper blogMapper ,CommentRepository commentRepository) {
		this.blogRepository = blogRepository;
		this.userRepository = userRepository;
		this.blogMapper = blogMapper;
		this.commentRepository = commentRepository;
	}

	public BlogResponseDTO createBlog(BlogRequestDTO requestDTO, String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Blog blog = new Blog();
		blog.setTitle(requestDTO.getTitle());
		blog.setDescription(requestDTO.getDescription());
		blog.setAuthor(user);

		Blog savedBlog = blogRepository.save(blog);
		return blogMapper.toBlogResponseDTO(savedBlog);
	}

	public List<BlogResponseDTO> getAllBlogs() {
		List<Blog> blogs = blogRepository.findAll();
		List<BlogResponseDTO> responseDTOs = new ArrayList<>();
		//List<Comment> commentListComments = new ArrayList<>();
	    for (Blog blog : blogs) {
	        BlogResponseDTO dto = blogMapper.toBlogResponseDTO(blog);
//	        commentListComments = commentRepository.findByBlogId(blog.getId());
//	        
//	        for (Comment comment : commentListComments) {
//	        	 dto.getComments().add(comment.getContent()); 
//	        }
	        
	        
	        
	        dto.setLikeCount(blog.getLikes() != null ? blog.getLikes().size() : 0);
	        dto.setCommentCount(blog.getComments() != null ? blog.getComments().size() : 0);
	        responseDTOs.add(dto);
	    }
	    
	   // System.out.println(responseDTO );
	    
		return responseDTOs;
	}

	public BlogDetailsDTO getBlogById(Long id) {
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
		return blogMapper.toBlogDetailsDTO(blog);
	}

	public void deleteBlog(Long id, String username) {
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

		if (!blog.getAuthor().getUsername().equals(username)) {
			throw new RuntimeException("You are not authorized to delete this blog");
		}
		blogRepository.delete(blog);
	}
}
