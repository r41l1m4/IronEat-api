package dev.ironia.ironeat.api.model.input;

import dev.ironia.ironeat.core.validation.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    @TaxaFrete
    private BigDecimal taxaFrete;

    @NotNull
    @Valid
    private CozinhaIdInputDTO cozinha;
}
