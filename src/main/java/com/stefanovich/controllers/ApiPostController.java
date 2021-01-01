package com.stefanovich.controllers;

import com.stefanovich.dto.LikeDto;
import com.stefanovich.dto.ListPostDto;
import com.stefanovich.dto.PostCreateDto;
import com.stefanovich.dto.PostIdDto;
import com.stefanovich.model.ModerationStatus;
import com.stefanovich.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class ApiPostController {
    private final PostService postService;

    @GetMapping
    public ListPostDto getAll(@RequestParam(defaultValue = "0", required = false) Integer offset,
                              @RequestParam(defaultValue = "5", required = false) Integer limit) {
        return ListPostDto.builder()
                .count(postService.getCount())
                .posts(postService.getAllPostsDto(offset, limit))
                .build();
    }

    @GetMapping("/search")
    public ListPostDto getPostsSearch(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                      @RequestParam(defaultValue = "5", required = false) Integer limit,
                                      @RequestParam String query) {
        long count = postService.getPostsDtoSearch(offset, limit, query).size();
        return ListPostDto.builder()
                .count(count)
                .posts(postService.getPostsDtoSearch(offset, limit, query))
                .build();
    }


    @GetMapping("/byDate")
    public ListPostDto getByDate(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "5", required = false) Integer limit,
                                 @RequestParam String date) {

        long count = postService.getPostsDtoByDate(offset, limit, date).size();
        return ListPostDto.builder()
                .count(count)
                .posts(postService.getPostsDtoByDate(offset, limit, date))
                .build();
    }

    @GetMapping("/byTag")
    public ListPostDto getByTags(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "5", required = false) Integer limit,
                                 @RequestParam(name = "tag") String tagName) {

        long count = postService.getByTagsDto(offset, limit, tagName).size();

        return ListPostDto.builder()
                .count(count)
                .posts(postService.getByTagsDto(offset, limit, tagName))
                .build();
    }


    @GetMapping("/moderation")
    public ListPostDto getByModeration(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                       @RequestParam(defaultValue = "5", required = false) Integer limit,
                                       @RequestParam ModerationStatus status) {

        long count = postService.getByModeration(offset, limit, status).size();
        return ListPostDto.builder()
                .count(count)
                .posts(postService.getByModeration(offset, limit, status))
                .build();
    }


    @GetMapping("/my")
    public ListPostDto getByModeration(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                       @RequestParam(defaultValue = "5", required = false) Integer limit,
                                       @RequestParam ModerationStatus status,
                                       @RequestParam Boolean isActive) {
        long count = postService.getByMyPosts(offset, limit, status, isActive).size();

        return ListPostDto.builder()
                .count(count)
                .posts(postService.getByMyPosts(offset, limit, status, isActive))
                .build();
    }

    @GetMapping("/{id}")
    public PostIdDto getByPostId(@PathVariable Integer id) {

        return postService.getByPostId(id);
    }


    @PostMapping
    public Map<String, Boolean> savePost(@RequestBody @Valid PostCreateDto postCreateDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", true);
        postService.savePostDto(postCreateDto);
        return map;
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveImage(@RequestPart MultipartFile image) throws IOException {
        return postService.savePicture(image);
    }

    @PutMapping("/{id}")
    public Map<String, Boolean> updatePost(@PathVariable Integer id, @RequestBody @Valid PostCreateDto postCreateDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", true);
        postService.update(id, postCreateDto);
        return map;
    }

    @PostMapping("/like")
    public Map<String, Boolean> putLike(@RequestBody LikeDto likeDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", postService.saveLike(likeDto));
        return map;

    }

    @PostMapping("/dislike")
    public Map<String, Boolean> putDisLike(@RequestBody LikeDto likeDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", postService.saveDisLike(likeDto));
        return map;

    }


}
