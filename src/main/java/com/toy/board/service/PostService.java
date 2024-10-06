package com.toy.board.service;

import com.toy.board.exception.post.PostNotFoundException;
import com.toy.board.exception.user.UserNotAllowedException;
import com.toy.board.exception.user.UserNotFoundException;
import com.toy.board.model.entity.UserEntity;
import com.toy.board.model.post.Post;
import com.toy.board.model.post.PostPatchRequestBody;
import com.toy.board.model.post.PostPostRequestBody;
import com.toy.board.model.entity.PostEntity;
import com.toy.board.model.user.User;
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

    public Post createPost(PostPostRequestBody postPostRequestBody, UserEntity currentUser) {
        var postEntity = postEntityRepository.save(
                PostEntity.of(postPostRequestBody.body(), currentUser)
        );
        return Post.from(postEntity);
    }

    public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody, UserEntity currentUser) {

        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        if(!postEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }
        postEntity.setBody(postPatchRequestBody.body());
        var updatedPostEntity = postEntityRepository.save(postEntity);
        return Post.from(updatedPostEntity);
    }

    public void deletePost(Long postId,  UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        if(!postEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }
        postEntityRepository.delete(postEntity);
    }
}

