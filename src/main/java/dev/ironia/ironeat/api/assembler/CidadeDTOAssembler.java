package dev.ironia.ironeat.api.assembler;

import dev.ironia.ironeat.api.model.output.CidadeOutputDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeOutputDTO toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeOutputDTO.class);
    }

    public List<CidadeOutputDTO> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
