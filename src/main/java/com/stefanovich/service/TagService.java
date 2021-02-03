package com.stefanovich.service;

import com.stefanovich.dto.TagDto;
import com.stefanovich.dto.mapper.TagDtoMapper;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Tags;
import com.stefanovich.repository.PostsRepository;
import com.stefanovich.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagsRepository tagsRepository;
    private final TagDtoMapper tagDtoMapper;
    private final PostsRepository postsRepository;

    public List<TagDto> getTagsDtoByQuery(String query) {
        List<Tags> tags = tagsRepository.findAllByQuery(query);
        List<TagDto> tagsDto = new ArrayList<>();
        Long countPosts = postsRepository.count();

        Map<Tags, Double> mapWithWeight = new HashMap<>();
        Double maxWeight = 0.0;

        for (Tags t : tags) {
            Long countOfPosts = postsRepository.countPostByTagId(t.getId());

            double currentWeight = (double) countOfPosts / countPosts;
            mapWithWeight.put(t, currentWeight);
            if (currentWeight > maxWeight) {
                maxWeight = currentWeight;
            }
        }

        for (Map.Entry<Tags, Double> entry : mapWithWeight.entrySet()) {
            tagsDto.add(TagDto.builder()
                    .name(entry.getKey().getName())
                    .weight(entry.getValue() / maxWeight)
                    .build());

        }
        return tagsDto;
    }
}
