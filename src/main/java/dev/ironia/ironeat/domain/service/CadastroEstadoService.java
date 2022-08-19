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
    private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com o código %d.";
    private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
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
                            MSG_ESTADO_NAO_ENCONTRADO,
                            id
                    )
            );
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            MSG_ESTADO_NAO_ENCONTRADO,
                            id
                    )
            );
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO,
                            id
                    )
            );
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRADO,
                                estadoId
                        )));
    }

}
