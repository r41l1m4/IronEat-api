package dev.ironia.ironeat.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.ironia.ironeat.domain.model.Estado;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
