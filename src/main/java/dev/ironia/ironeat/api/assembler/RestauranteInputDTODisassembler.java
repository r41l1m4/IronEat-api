package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.input.CozinhaIdInputDTO;
import dev.ironia.ironeat.api.model.input.RestauranteInputDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteInputDTO fromOutputDTOToInputDTO(RestauranteOutputDTO restauranteOutputDTO) {
        return modelMapper.map(restauranteOutputDTO, RestauranteInputDTO.class);
    }

    public Restaurante fromModel(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }
}
