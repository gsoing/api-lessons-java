package com.tplock.locking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class ErrorDefinitionType {
    @JsonProperty("errorCode")
    private String errorCode = null;

    @JsonProperty("errorMessage")
    private String errorMessage = null;

    public ErrorDefinitionType(String code, String error){
        this.errorCode = code;
        this.errorMessage = error;
    }

    public ErrorDefinitionType errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorDefinitionType errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



}
