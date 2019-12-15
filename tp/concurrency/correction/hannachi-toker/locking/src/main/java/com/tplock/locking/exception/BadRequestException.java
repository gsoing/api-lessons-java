package com.tplock.locking.exception;

import com.tplock.locking.dto.ErrorDefinition;
import com.tplock.locking.dto.ErrorDefinitionType;
import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionType errorDefinitionType = new ErrorDefinitionType("400"," the request you sent to the website server, was somehow incorrect or corrupted and the server couldn't understand it");
        this.httpCode = HttpStatus.BAD_REQUEST;
        this.errorDefinition.addErrorsItem(errorDefinitionType);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.TECHNICAL);
    }
}
