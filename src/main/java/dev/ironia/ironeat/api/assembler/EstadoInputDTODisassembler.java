package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.input.EstadoInputDTO;
import dev.ironia.ironeat.api.model.output.EstadoOutputDTO;
import dev.ironia.ironeat.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDTODisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public EstadoInputDTO fromOutputDTOToInputDTO(EstadoOutputDTO estadoOutputDTO) {
        return modelMapper.map(estadoOutputDTO, EstadoInputDTO.class);
    }

    public Estado fromModel(EstadoInputDTO estadoInputDTO) {
        return modelMapper.map(estadoInputDTO, Estado.class);
    }

    public void copyToDomainObject(EstadoInputDTO estadoInputDTO, Estado estado) {
        modelMapper.map(estadoInputDTO, estado);
    }
}
