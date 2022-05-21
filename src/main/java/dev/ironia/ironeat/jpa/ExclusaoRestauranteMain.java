package dev.ironia.ironeat.jpa;

import dev.ironia.ironeat.IronEatApiApplication;
import dev.ironia.ironeat.domain.model.Restaurante;
import dev.ironia.ironeat.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ExclusaoRestauranteMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(IronEatApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        restaurantes.delete(restaurante);
    }
}
