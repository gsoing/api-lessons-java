package com.tplock.locking.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorDefinition   {

    public enum ErrorTypeEnum {
        TECHNICAL("TECHNICAL"),

        FUNCTIONAL("FUNCTIONAL");

        private String value;

        ErrorTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static ErrorTypeEnum fromValue(String text) {
            for (ErrorTypeEnum b : ErrorTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("errorType")
    private ErrorTypeEnum errorType = null;

    @JsonProperty("errors")
    private List<ErrorDefinitionType> errors = null;

    public ErrorDefinition errorType(ErrorTypeEnum errorType) {
        this.errorType = errorType;
        return this;
    }



    public ErrorTypeEnum getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorTypeEnum errorType) {
        this.errorType = errorType;
    }

    public ErrorDefinition errors(List<ErrorDefinitionType> errors) {
        this.errors = errors;
        return this;
    }

    public ErrorDefinition addErrorsItem(ErrorDefinitionType errorsItem) {
        if (this.errors == null) {
            this.errors = new ArrayList<ErrorDefinitionType>();
        }
        this.errors.add(errorsItem);
        return this;
    }


    public List<ErrorDefinitionType> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDefinitionType> errors) {
        this.errors = errors;
    }


}

