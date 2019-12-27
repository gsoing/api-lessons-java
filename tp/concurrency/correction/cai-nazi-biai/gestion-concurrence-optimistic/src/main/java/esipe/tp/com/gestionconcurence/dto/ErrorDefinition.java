package esipe.tp.com.gestionconcurence.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class ErrorDefinition implements Serializable {
    private ErrorType errorType;
    private ArrayList<Error> errors;
}
