package com.toy.board.model.user;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestBody(
        @NotEmpty
        String username,
        String password
){



}
