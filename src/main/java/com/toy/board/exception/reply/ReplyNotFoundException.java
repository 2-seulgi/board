package com.toy.board.exception.reply;

import com.toy.board.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class ReplyNotFoundException extends ClientErrorException {
    public ReplyNotFoundException(){
        super(HttpStatus.NOT_FOUND,"reply not found.");
    }

    public ReplyNotFoundException(Long replyId){
        super(HttpStatus.NOT_FOUND,"reply with postId " + replyId +" not found.");
    }

    public ReplyNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
