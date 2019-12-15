package fr.esipe.locking.errors;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDefinition {

    private String errorType;
    private List<Error> errors;

}
