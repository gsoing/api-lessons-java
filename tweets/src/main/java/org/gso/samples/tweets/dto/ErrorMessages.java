package org.gso.samples.tweets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorMessages implements Serializable {
    private ErrorMessageType type;
    private List<ErrorMessage> messages;

    public ErrorMessages(ErrorMessageType type, ErrorMessage...errorMessages){
            this.type = type;
            this.messages = Arrays.asList(errorMessages);
    }
}
