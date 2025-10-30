package org.gso.samples.tweets.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.gso.samples.tweets.model.User;


public record UserDto (@NotBlank String nickname, @Email String mail) {

    public User toEntity() {
        return User.builder()
                .mail(mail)
                .nickname(nickname)
                .build();
    }
}
