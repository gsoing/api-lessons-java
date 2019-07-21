package com.gso.samples.tweet.model;

import com.gso.samples.tweet.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class User {
    @NotBlank
    private String nickname;
    @Email
    private String mail;

    public UserDto toDto(){
        return UserDto.builder()
                .mail(mail)
                .nickname(nickname)
                .build();
    }
}
