package org.gso.samples.tweets.model;

import lombok.Builder;
import lombok.Data;
import org.gso.samples.tweets.dto.UserDto;

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
