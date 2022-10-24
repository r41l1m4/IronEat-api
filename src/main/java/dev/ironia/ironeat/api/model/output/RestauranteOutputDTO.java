package dev.ironia.ironeat.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutputDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaOutputDTO cozinha;
}
