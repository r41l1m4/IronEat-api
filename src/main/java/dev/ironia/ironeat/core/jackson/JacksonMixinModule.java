package dev.ironia.ironeat.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.ironia.ironeat.api.model.mixin.CidadeMixin;
import dev.ironia.ironeat.api.model.mixin.CozinhaMixin;
import dev.ironia.ironeat.api.model.mixin.EnderecoMixin;
import dev.ironia.ironeat.api.model.mixin.RestauranteMixin;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Endereco;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Endereco.class, EnderecoMixin.class);
    }
}
