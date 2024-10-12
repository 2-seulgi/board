package com.toy.board.service;

import com.toy.board.exception.post.PostNotFoundException;
import com.toy.board.exception.reply.ReplyNotFoundException;
import com.toy.board.exception.user.UserNotAllowedException;
import com.toy.board.exception.user.UserNotFoundException;
import com.toy.board.model.entity.PostEntity;
import com.toy.board.model.entity.ReplyEntity;
import com.toy.board.model.entity.UserEntity;
import com.toy.board.model.post.Post;
import com.toy.board.model.post.PostPatchRequestBody;
import com.toy.board.model.post.PostPostRequestBody;
import com.toy.board.model.reply.Reply;
import com.toy.board.model.reply.ReplyPatchRequestBody;
import com.toy.board.model.reply.ReplyPostRequestBody;
import com.toy.board.repository.PostEntityRepository;
import com.toy.board.repository.ReplyEntityRepository;
import com.toy.board.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    @Autowired private ReplyEntityRepository replyEntityRepository;
    @Autowired private PostEntityRepository postEntityRepository;
    @Autowired private UserEntityRepository userEntityRepository;

    public List<Reply> getRepliesByPostId(Long postId){

        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );

        var replyEntities = replyEntityRepository.findByPost(postEntity);

        return replyEntities.stream().map(Reply::from).toList();
    }

    public Reply createReply(Long postId, ReplyPostRequestBody replyPostRequestBody, UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );

        var replyEntity = replyEntityRepository.save(
                ReplyEntity.of(replyPostRequestBody.body(), currentUser, postEntity)
        );
        return Reply.from(replyEntity);
    }

    public Reply updateReply(Long postId, Long replyId, ReplyPatchRequestBody replyPatchRequestBody, UserEntity currentUser) {

        postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        var replyEntity = replyEntityRepository.findById(replyId)
                .orElseThrow(
                        ()-> new ReplyNotFoundException(replyId)
                );
        if(!replyEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }
        replyEntity.setBody(replyPatchRequestBody.body());
        var updatedReplyEntity = replyEntityRepository.save(replyEntity);
        return Reply.from(updatedReplyEntity);
    }

    public void deleteReply(Long postId, Long replyId, UserEntity currentUser) {
         postEntityRepository.findById(postId)
                .orElseThrow(
                        ()-> new PostNotFoundException(postId)
                );
        var replyEntity = replyEntityRepository.findById(replyId)
                .orElseThrow(
                        ()-> new ReplyNotFoundException(replyId)
                );
        if(!replyEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }
        replyEntityRepository.delete(replyEntity);
    }


}

