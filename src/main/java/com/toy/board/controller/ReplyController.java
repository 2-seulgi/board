package com.toy.board.controller;

import com.toy.board.model.entity.UserEntity;
import com.toy.board.model.post.Post;
import com.toy.board.model.post.PostPatchRequestBody;
import com.toy.board.model.post.PostPostRequestBody;
import com.toy.board.model.reply.Reply;
import com.toy.board.model.reply.ReplyPatchRequestBody;
import com.toy.board.model.reply.ReplyPostRequestBody;
import com.toy.board.service.PostService;
import com.toy.board.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/post/{postId}/replies")
@RestController
public class ReplyController {

    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public ResponseEntity<List<Reply>> getRepliesByPostId(@PathVariable Long postId) {
        logger.info("GET /api/v1/posts");
        var replies = replyService.getRepliesByPostId(postId);
        return ResponseEntity.ok(replies);
    }

    @PostMapping
    public ResponseEntity<Reply> createReply(
            @PathVariable Long postId,
            @RequestBody ReplyPostRequestBody replyPostRequestBody , Authentication authentication) {
        var reply = replyService.createReply(postId, replyPostRequestBody,(UserEntity)authentication.getPrincipal());
        return ResponseEntity.ok(reply);
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<Reply> updateReply(
            @PathVariable Long postId,
            @PathVariable Long replyId,
            @RequestBody ReplyPatchRequestBody replyPatchRequestBody,
            Authentication authentication
    ) {

        var reply = replyService.updateReply(postId, replyId, replyPatchRequestBody, (UserEntity)authentication.getPrincipal());
        return ResponseEntity.ok(reply);
    }


    @DeleteMapping ("/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long postId,
            @PathVariable Long replyId,
            Authentication authentication ) {
        replyService.deleteReply(postId, replyId, (UserEntity)authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}
