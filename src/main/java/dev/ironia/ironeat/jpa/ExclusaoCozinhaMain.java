package dev.ironia.ironeat.jpa;

import dev.ironia.ironeat.IronEatApiApplication;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ExclusaoCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(IronEatApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);

        //cozinhas.remover(cozinha);
    }
}
