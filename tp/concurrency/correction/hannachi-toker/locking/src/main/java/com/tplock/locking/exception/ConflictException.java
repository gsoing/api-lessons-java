package com.tplock.locking.exception;

import com.tplock.locking.dto.ErrorDefinition;
import com.tplock.locking.dto.ErrorDefinitionType;
import com.tplock.locking.model.Lock;
import org.springframework.http.HttpStatus;

public class ConflictException extends CustomException {
    private ErrorDefinition errorDefinition;
    public ConflictException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionType err = new ErrorDefinitionType("409","The request could not be completed due to a conflict with the current state of the target resource");
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
        this.errorDefinition.addErrorsItem(err);
        this.httpCode = HttpStatus.CONFLICT;
    }
    public ConflictException(String message, Lock l) {
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionType err;
        if(l!=null)
            err = new ErrorDefinitionType("409",message+ l.getOwner());
        else
            err = new ErrorDefinitionType("409", message);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
        this.errorDefinition.addErrorsItem(err);
        this.httpCode = HttpStatus.CONFLICT;
    }
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
}
