package com.ackincolor.tpconcurrence.exceptions;

import com.ackincolor.tpconcurrence.entities.ErrorDefinition;
import com.ackincolor.tpconcurrence.entities.ErrorDefinitionErrors;
import org.springframework.http.HttpStatus;

public class NoContentException extends CustomException {
    public NoContentException(){
        this.errorDefinition = new ErrorDefinition();
        ErrorDefinitionErrors errorDefinitionErrors = new ErrorDefinitionErrors("404","Document non trouvé");
        this.httpCode = HttpStatus.NO_CONTENT;
        this.errorDefinition.addErrorsItem(errorDefinitionErrors);
        this.errorDefinition.setErrorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL);
    }
}
