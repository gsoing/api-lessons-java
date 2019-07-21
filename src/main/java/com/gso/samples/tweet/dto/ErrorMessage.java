package com.gso.samples.tweet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable {

    private String code;
    private String message;

}
