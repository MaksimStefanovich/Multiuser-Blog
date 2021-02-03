package com.stefanovich.service;

import com.stefanovich.dto.CommentCreateDto;
import com.stefanovich.model.PostComments;
import com.stefanovich.model.Posts;
import com.stefanovich.repository.PostCommentsRepository;
import com.stefanovich.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostCommentsRepository postCommentsRepository;
    private final PostsRepository postsRepository;
    private final AuthService authService;

    public Map<String, Integer> saveCommentDto(CommentCreateDto commentCreateDto) {
        PostComments postComments = new PostComments();
        Integer id = commentCreateDto.getPostId();
        Posts post = postsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post with id = " + id + " not found"));
        postComments.setMessages(post);
        postComments.setText(commentCreateDto.getText());

        if (commentCreateDto.getParentId() != null) {
            postComments.setParent(postCommentsRepository.findById(commentCreateDto.getParentId()).orElseThrow(
                    () -> new EntityNotFoundException("postComment with id = " + id + " not found")));
        }

        postComments.setUser(authService.getCurrentUser());
        postComments.setTime(LocalDateTime.now());
        postCommentsRepository.save(postComments);
        return Map.of("id", postComments.getId());
    }
}
