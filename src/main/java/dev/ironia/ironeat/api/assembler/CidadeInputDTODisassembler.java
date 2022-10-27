package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.input.CidadeInputDTO;
import dev.ironia.ironeat.api.model.input.RestauranteInputDTO;
import dev.ironia.ironeat.api.model.output.CidadeOutputDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeInputDTO fromOutputDTOToInputDTO(CidadeOutputDTO cidadeOutputDTO) {
        return modelMapper.map(cidadeOutputDTO, CidadeInputDTO.class);
    }

    public Cidade fromModel(CidadeInputDTO cidadeInputDTO) {
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        //Resolved [org.springframework.orm.jpa.JpaSystemException: identifier of an instance of dev.ironia.ironeat.domain.model.Estado was altered from 2 to 3; nested exception is org.hibernate.HibernateException: identifier of an instance of dev.ironia.ironeat.domain.model.Estado was altered from 2 to 3]
        //Linha abaixo para resolver o B.O. acima.
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInputDTO, cidade);
    }
}
