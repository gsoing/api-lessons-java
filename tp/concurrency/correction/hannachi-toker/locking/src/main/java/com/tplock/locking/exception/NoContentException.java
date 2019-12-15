package com.tplock.locking.exception;

import com.tplock.locking.dto.ErrorDefinition;
import com.tplock.locking.dto.ErrorDefinitionType;
import org.springframework.http.HttpStatus;

public class NoContentException extends CustomException {
    public NoContentException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionType errorDefinitionType = new ErrorDefinitionType("404","File not found");
        this.httpCode = HttpStatus.NO_CONTENT;
        this.errorDefinition.addErrorsItem(errorDefinitionType);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
}
