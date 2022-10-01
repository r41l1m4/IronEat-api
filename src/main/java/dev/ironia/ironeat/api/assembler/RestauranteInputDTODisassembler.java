package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.input.CozinhaIdInputDTO;
import dev.ironia.ironeat.api.model.input.RestauranteInputDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDTODisassembler {

    public static RestauranteInputDTO fromOutputDTOToInputDTO(RestauranteOutputDTO restauranteOutputDTO) {
        CozinhaIdInputDTO cozinhaIdInputDTO = new CozinhaIdInputDTO();
        cozinhaIdInputDTO.setId(restauranteOutputDTO.getCozinha().getId());

        RestauranteInputDTO restauranteInputDTO = new RestauranteInputDTO();
        restauranteInputDTO.setNome(restauranteOutputDTO.getNome());
        restauranteInputDTO.setTaxaFrete(restauranteOutputDTO.getTaxaFrete());
        restauranteInputDTO.setCozinha(cozinhaIdInputDTO);

        return restauranteInputDTO;
    }

    public static Restaurante fromModel(RestauranteInputDTO restauranteInputDTO) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInputDTO.getNome());
        restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInputDTO.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return  restaurante;
    }
}
