package org.esipe.springbootfromswagger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable {
    private String errorCode;
    private String errorMessage;

}
