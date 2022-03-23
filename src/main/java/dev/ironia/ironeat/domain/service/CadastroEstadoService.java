package dev.ironia.ironeat.domain.service;

import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CadastroEstadoService {
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.adicionar(estado);
    }

    public void excluir(Long id) {
        try {
            estadoRepository.remover(id);
        }catch (IllegalArgumentException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe um cadastro de estado com o código %d.",
                            id
                    )
            );
        }
    }

}
