package dev.ironia.ironeat.domain.service;

import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.CidadeRepository;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    private CidadeRepository cidadeRepository;
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.porId(estadoId);

        if(estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe cadastro de estado com o código %d.",
                            estadoId
                    )
            );
        }
        cidade.setEstado(estado);
        return cidadeRepository.adicionar(cidade);
    }

    public void excluir(Long id) {
        try{
            cidadeRepository.remover(id);
        }catch (IllegalArgumentException er){
            throw new EntidadeNaoEncontradaException(
                    String.format(
                            "Não existe um cadastro de cidade com o código %d.",
                            id
                    )
            );
        }
    }
}
