package dev.ironia.ironeat.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    private EstadoIdInputDTO estado;
}
