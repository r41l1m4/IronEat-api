package dev.ironia.ironeat.domain.service;

import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Restaurante;
import dev.ironia.ironeat.domain.repository.CozinhaRepository;
import dev.ironia.ironeat.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {
    private RestauranteRepository restauranteRepository;
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o código %d.",
                                cozinhaId
                        )
                ));
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

}
