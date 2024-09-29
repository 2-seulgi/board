package com.toy.board.model.user;

import com.toy.board.model.entity.UserEntity;

import java.time.ZonedDateTime;

public record User(
        Long userId,
        String username,
        String profile,
        String description,
        ZonedDateTime createDateTime,
        ZonedDateTime updateDateTime) {

        public static User from(UserEntity userEntity){
            return new User(
                    userEntity.getUserId(),
                    userEntity.getUsername(),
                    userEntity.getProfile(),
                    userEntity.getDescription(),
                    userEntity.getCreateDateTime(),
                    userEntity.getUpdateDateTime()
            );
        }
}
