package com.toy.board.model.reply;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.toy.board.model.entity.PostEntity;
import com.toy.board.model.entity.ReplyEntity;
import com.toy.board.model.post.Post;
import com.toy.board.model.user.User;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Reply(
        Long postId,
        String body,
        User user,
        Post post,
        ZonedDateTime createDateTime,
        ZonedDateTime updateDateTime,
        ZonedDateTime deleteDataTime){

    public static Reply from(ReplyEntity replyEntity){
        return new Reply(
                replyEntity.getReplyId(),
                replyEntity.getBody(),
                User.from(replyEntity.getUser()),
                Post.from(replyEntity.getPost()),
                replyEntity.getCreateDateTime(),
                replyEntity.getUpdateDateTime(),
                replyEntity.getDeleteDateTime()
        );
    }
}
