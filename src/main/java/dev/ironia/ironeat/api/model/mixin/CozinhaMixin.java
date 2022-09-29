package dev.ironia.ironeat.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ironia.ironeat.domain.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class CozinhaMixin {
    @JsonIgnore // faz com que o jackson não serialize esse atributo no json
    private List<Restaurante> restaurantes = new ArrayList<>();
}
