package dev.ironia.ironeat.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.ironia.ironeat.domain.model.Cidade;

public class EnderecoMixin {

    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Cidade cidade;
}
