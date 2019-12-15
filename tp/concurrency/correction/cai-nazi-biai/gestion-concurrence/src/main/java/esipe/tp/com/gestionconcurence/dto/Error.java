package esipe.tp.com.gestionconcurence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class Error implements Serializable {

    private String code;
    private String message;

}
