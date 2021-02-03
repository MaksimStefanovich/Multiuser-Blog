package com.stefanovich.controllers;

import com.stefanovich.dto.LikeDto;
import com.stefanovich.dto.ListPostDto;
import com.stefanovich.dto.PostCreateDto;
import com.stefanovich.dto.PostIdDto;
import com.stefanovich.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class ApiPostController {
    private final PostService postService;

    @GetMapping
    public ListPostDto getAll(@RequestParam(defaultValue = "0", required = false) Integer offset,
                              @RequestParam(defaultValue = "5", required = false) Integer limit,
                              @RequestParam String mode) {
        return postService.getAllPostsDto(offset, limit, mode);
    }

    @GetMapping("/search")
    public ListPostDto getPostsSearch(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                      @RequestParam(defaultValue = "5", required = false) Integer limit,
                                      @RequestParam String query) {
        return postService.getPostsDtoSearch(offset, limit, query);
    }

    @GetMapping("/byDate")
    public ListPostDto getByDate(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "5", required = false) Integer limit,
                                 @RequestParam String date) {

        return postService.getPostsDtoByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    public ListPostDto getByTags(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "5", required = false) Integer limit,
                                 @RequestParam(name = "tag") String tagName) {

        return postService.getByTagsDto(offset, limit, tagName);
    }

    @GetMapping("/moderation")
    public ListPostDto getByModeration(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                       @RequestParam(defaultValue = "10", required = false) Integer limit,
                                       @RequestParam String status) {

        return postService.getByModeration(offset, limit, status);
    }

    @GetMapping("/my")
    public ListPostDto getByMyPost(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                   @RequestParam(defaultValue = "5", required = false) Integer limit,
                                   @RequestParam String status) {

        return postService.getByMyPosts(offset, limit, status);
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
