package dev.ironia.ironeat.domain.service;

import dev.ironia.ironeat.domain.exception.CidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    private CidadeRepository cidadeRepository;
    private CadastroEstadoService cadastroEstadoService;

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id) {
        try{
            cidadeRepository.deleteById(id);
        }catch (IllegalArgumentException | EmptyResultDataAccessException er){
            throw new CidadeNaoEncontradaException(id);
        }catch (DataIntegrityViolationException e){
            throw new CidadeNaoEncontradaException(id);
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }
}
