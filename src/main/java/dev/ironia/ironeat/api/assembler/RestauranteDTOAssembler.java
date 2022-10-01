package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.CozinhaDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    public static RestauranteOutputDTO toModel(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteOutputDTO restauranteOutputDTO = new RestauranteOutputDTO();
        restauranteOutputDTO.setId(restaurante.getId());
        restauranteOutputDTO.setNome(restaurante.getNome());
        restauranteOutputDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteOutputDTO.setCozinha(cozinhaDTO);
        return restauranteOutputDTO;
    }

    public static List<RestauranteOutputDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(RestauranteDTOAssembler::toModel)
                .collect(Collectors.toList());
    }
}
