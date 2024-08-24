package com.toy.board.controller;

import com.toy.board.model.Post;
import com.toy.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired private PostService postService;

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<Post>> getPosts(){
        List<Post> posts = postService.getPosts();

        return ResponseEntity.ok(posts);
    }


    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Post> getPost(
            @PathVariable Long postId
    ){
        Optional<Post> matchingPost = postService.getPost(postId);

        return matchingPost
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

}
