package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.output.EstadoOutputDTO;
import dev.ironia.ironeat.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoOutputDTO toModel(Estado estado) {
        return modelMapper.map(estado, EstadoOutputDTO.class);
    }

    public List<EstadoOutputDTO> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
