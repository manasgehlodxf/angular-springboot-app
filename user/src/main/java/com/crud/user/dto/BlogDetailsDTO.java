package com.crud.user.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BlogDetailsDTO {
	
	
	private Long id;
	private String title;
	private String description;
	private String authorUsername;
	private LocalDateTime createdAt;
	private List<CommentResponseDTO> comments;
	private int likeCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<CommentResponseDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponseDTO> comments) {
		this.comments = comments;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

}