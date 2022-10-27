package dev.ironia.ironeat.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.ironia.ironeat.api.model.mixin.CidadeMixin;
import dev.ironia.ironeat.api.model.mixin.EnderecoMixin;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Endereco.class, EnderecoMixin.class);
    }
}
