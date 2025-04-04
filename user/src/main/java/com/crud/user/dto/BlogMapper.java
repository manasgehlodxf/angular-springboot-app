package com.crud.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.crud.user.entity.Blog;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    BlogResponseDTO toBlogResponseDTO(Blog blog);

    @Mapping(source = "author.username", target = "authorUsername")
    BlogDetailsDTO toBlogDetailsDTO(Blog blog);
}

