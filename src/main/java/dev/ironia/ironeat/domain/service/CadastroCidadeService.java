package dev.ironia.ironeat.domain.service;

import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.CidadeRepository;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    private CidadeRepository cidadeRepository;
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if(estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe cadastro de estado com o código %d.",
                            estadoId
                    )
            );
        }
        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id) {
        try{
            cidadeRepository.deleteById(id);
        }catch (IllegalArgumentException er){
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe um cadastro de cidade com o código %d.",
                            id
                    )
            );
        }catch (EmptyResultDataAccessException erd) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com o código %d.",
                            id
                    )
            );
        }
    }
}
