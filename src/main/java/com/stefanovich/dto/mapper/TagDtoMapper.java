package com.stefanovich.dto.mapper;

import com.stefanovich.dto.TagDto;
import com.stefanovich.model.Tags;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagDtoMapper {
    private final ModelMapper modelMapper;

    public TagDto convertToTagsDTO(Tags tags) {
        return modelMapper
                .map(tags, TagDto.class);
    }

    public List<TagDto> convertToDtoList(List<Tags> tags) {
        return tags.stream().map(this::convertToTagsDTO).collect(Collectors.toList());
    }
}
