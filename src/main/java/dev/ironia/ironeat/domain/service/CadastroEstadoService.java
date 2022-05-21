package dev.ironia.ironeat.domain.service;

import dev.ironia.ironeat.domain.exception.EntidadeEmUsoException;
import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CadastroEstadoService {
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluir(Long id) {
        try {
            estadoRepository.deleteById(id);
        }catch (IllegalArgumentException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe um cadastro de estado com o código %d.",
                            id
                    )
            );
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe um cadastro de estado com o código %d.",
                            id
                    )
            );
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso",
                            id
                    )
            );
        }
    }

}
