package dev.ironia.ironeat.api.model.output;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoOutputDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;
}
