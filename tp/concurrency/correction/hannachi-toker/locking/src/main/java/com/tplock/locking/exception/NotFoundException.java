package com.tplock.locking.exception;

import com.tplock.locking.dto.ErrorDefinition;
import com.tplock.locking.dto.ErrorDefinitionType;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionType errorDefinitionType = new ErrorDefinitionType("404","Document not found");
        this.httpCode = HttpStatus.NOT_FOUND;
        this.errorDefinition.addErrorsItem(errorDefinitionType);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
    public NotFoundException(String msg){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionType errorDefinitionType = new ErrorDefinitionType("404",msg);
        this.httpCode = HttpStatus.NOT_FOUND;
        this.errorDefinition.addErrorsItem(errorDefinitionType);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
}
