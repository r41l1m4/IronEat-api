package dev.ironia.ironeat.jpa;

import dev.ironia.ironeat.IronEatApiApplication;
import dev.ironia.ironeat.domain.model.Restaurante;
import dev.ironia.ironeat.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(IronEatApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Rodeio's");
        restaurante1.setTaxaFrete(BigDecimal.valueOf(32));

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("La Casa de Pastel");
        restaurante2.setTaxaFrete(BigDecimal.valueOf(8.49));

        restaurantes.save(restaurante1);
        restaurantes.save(restaurante2);
    }
}
