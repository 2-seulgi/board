package com.toy.board.controller;

import com.toy.board.model.Post;
import com.toy.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    @Autowired private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(){
        List<Post> posts = postService.getPosts();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(
            @PathVariable Long postId
    ){
        Optional<Post> matchingPost = postService.getPostByPostId(postId);

        return matchingPost.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

}
