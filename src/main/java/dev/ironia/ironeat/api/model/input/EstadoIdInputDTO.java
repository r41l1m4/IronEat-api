package dev.ironia.ironeat.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdInputDTO {

    @NotNull
    private Long id;
}
