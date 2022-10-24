package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.input.CozinhaInputDTO;
import dev.ironia.ironeat.api.model.output.CozinhaOutputDTO;
import dev.ironia.ironeat.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDTODisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaInputDTO fromOutputDTOToInputDTO(CozinhaOutputDTO cozinhaOutputDTO) {
        return modelMapper.map(cozinhaOutputDTO, CozinhaInputDTO.class);
    }

    public Cozinha fromModel(CozinhaInputDTO cozinhaInputDTO) {
        return modelMapper.map(cozinhaInputDTO, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
        modelMapper.map(cozinhaInputDTO, cozinha);
    }
}
