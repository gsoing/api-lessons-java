package com.gso.samples.tweet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gso.samples.tweet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @NotBlank
    private String nickname;
    @Email
    private String mail;

    public User toEntity() {
        return User.builder()
                .mail(mail)
                .nickname(nickname)
                .build();
    }
}
