package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.output.CozinhaOutputDTO;
import dev.ironia.ironeat.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaOutputDTO toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaOutputDTO.class);
    }

    public List<CozinhaOutputDTO> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
