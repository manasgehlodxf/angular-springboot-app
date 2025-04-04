package com.crud.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.crud.user.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.name", target = "authorUsername")
    CommentResponseDTO toCommentResponseDTO(Comment comment);
}