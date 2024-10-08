package com.toy.board.service;

import com.toy.board.exception.post.PostNotFoundException;
import com.toy.board.model.post.Post;
import com.toy.board.model.post.PostPatchRequestBody;
import com.toy.board.model.post.PostPostRequestBody;
import com.toy.board.model.entity.PostEntity;
import com.toy.board.repository.PostEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired private PostEntityRepository postEntityRepository;

    public List<Post> getPosts(){
        var postEntities  = postEntityRepository.findAll();

        return postEntities.stream().map(Post::from).toList();
    }

    public Post getPostByPostId(Long postId){
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        return Post.from(postEntity);
    }

    public Post createPost(PostPostRequestBody postPostRequestBody ) {
        var postEntity = new PostEntity();
        postEntity.setBody(postPostRequestBody.body());

        var savedPostEntity = postEntityRepository.save(postEntity);

        return Post.from(savedPostEntity);
    }

    public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody) {

        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        postEntity.setBody(postPatchRequestBody.body());
        PostEntity updatedPostEntity = postEntityRepository.save(postEntity);

        return Post.from(updatedPostEntity);
    }

    public void deletePost(Long postId) {
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        postEntityRepository.delete(postEntity);
    }
}

