package com.stefanovich.controllers;

import com.stefanovich.dto.LikeDto;
import com.stefanovich.dto.ListPostDto;
import com.stefanovich.dto.PostCreateDto;
import com.stefanovich.dto.PostIdDto;
import com.stefanovich.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
@Tag(name = "post API", description = "Метод получения постов со всей сопутствующей информацией для главной страницы и подразделов")
public class ApiPostController {
    private final PostService postService;

    @Operation(summary = "Метод возвращает все посты",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping
    public ListPostDto getAll(@RequestParam(defaultValue = "0", required = false) Integer offset,
                              @RequestParam(defaultValue = "5", required = false) Integer limit,
                              @RequestParam String mode) {
        return postService.getAllPostsDto(offset, limit, mode);
    }

    @Operation(summary = "Метод возвращает посты, соответствующие поисковому запросу - строке query",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "post by id is found",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/search")
    public ListPostDto getPostsSearch(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                      @RequestParam(defaultValue = "5", required = false) Integer limit,
                                      @RequestParam String query) {
        return postService.getPostsDtoSearch(offset, limit, query);
    }

    @Operation(summary = "Выводит посты за указанную дату, переданную в запросе в параметре date",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/byDate")
    public ListPostDto getByDate(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "5", required = false) Integer limit,
                                 @RequestParam String date) {

        return postService.getPostsDtoByDate(offset, limit, date);
    }

    @Operation(summary = "Метод выводит список постов, привязанных к тэгу, который был передан методу в качестве параметра tag.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/byTag")
    public ListPostDto getByTags(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "5", required = false) Integer limit,
                                 @RequestParam(name = "tag") String tagName) {

        return postService.getByTagsDto(offset, limit, tagName);
    }

    @Operation(summary = "Метод выводит все посты, которые требуют модерационных действий \" +\n" +
            "            \"(которые нужно утвердить или отклонить) или над которыми мною были совершены модерационные действия: \" +\n" +
            "            \"которые я отклонил или утвердил \" +\n" +
            "            \"(это определяется полями moderation_status и moderator_id в таблице posts базы данных).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/moderation")
    public ListPostDto getByModeration(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                       @RequestParam(defaultValue = "10", required = false) Integer limit,
                                       @RequestParam String status) {

        return postService.getByModeration(offset, limit, status);
    }

    @Operation(summary = "Метод выводит только те посты, которые создал я " +
            "(в соответствии с полем user_id в таблице posts базы данных).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/my")
    public ListPostDto getByMyPost(@RequestParam(defaultValue = "0", required = false) Integer offset,
                                   @RequestParam(defaultValue = "5", required = false) Integer limit,
                                   @RequestParam String status) {

        return postService.getByMyPosts(offset, limit, status);
    }

    @Operation(summary = "get post by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "post by id is found",
                            content = @Content(schema = @Schema(implementation = PostIdDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "post by id isn't found"
                    )
            }
    )
    @GetMapping("/{id}")
    public PostIdDto getByPostId(@PathVariable @Parameter(description = "post id", required = true) Integer id) {

        return postService.getByPostId(id);
    }

    @Operation(summary = "Метод изменяет данные поста с идентификатором ID на те," +
            " которые пользователь ввёл в форму публикации.",
            responses = {
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    @PostMapping
    public Map<String, Boolean> savePost(@RequestBody @Valid PostCreateDto postCreateDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", true);
        postService.savePostDto(postCreateDto);
        return map;
    }
    @Operation(summary = "update post",
            responses = {
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/{id}")
    public Map<String, Boolean> updatePost(@PathVariable Integer id, @RequestBody @Valid PostCreateDto postCreateDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", true);
        postService.update(id, postCreateDto);
        return map;
    }
    @Operation(summary = "Метод сохраняет в таблицу post_votes лайк текущего авторизованного пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": false\n" +
                                    "}"
                    )
            }
    )
    @PostMapping("/like")
    public Map<String, Boolean> putLike(@RequestBody LikeDto likeDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", postService.saveLike(likeDto));
        return map;

    }
    @Operation(summary = "Метод сохраняет в таблицу post_votes дизлайк текущего авторизованного пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": false\n" +
                                    "}"
                    )
            }
    )
    @PostMapping("/dislike")
    public Map<String, Boolean> putDisLike(@RequestBody LikeDto likeDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", postService.saveDisLike(likeDto));
        return map;
    }
}
