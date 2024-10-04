package org.gso.samples.tweets.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.gso.samples.tweets.dto.UserDto;

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
