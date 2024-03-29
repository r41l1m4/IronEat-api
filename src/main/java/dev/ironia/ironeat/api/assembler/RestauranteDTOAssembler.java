package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteOutputDTO toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteOutputDTO.class);
    }

    public List<RestauranteOutputDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
