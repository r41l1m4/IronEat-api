package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.input.RestauranteInputDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Cozinha;
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

    public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        //Resolved [org.springframework.orm.jpa.JpaSystemException: identifier of an instance of dev.ironia.ironeat.domain.model.Cozinha was altered from 2 to 3; nested exception is org.hibernate.HibernateException: identifier of an instance of dev.ironia.ironeat.domain.model.Cozinha was altered from 2 to 3]
        //Linha abaixo para resolver o BO acima.
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInputDTO, restaurante);
    }
}
