package com.modelisation.tp.tpmodelisation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Error Message with its parameters
 */
@Builder
@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable {

    private String errorCode;
    private String errorMessage;

}
