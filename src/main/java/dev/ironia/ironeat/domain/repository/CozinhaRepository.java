package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
    List<Cozinha> findByNome(String nome);
    List<Cozinha> findByNomeContaining(String nome);

    boolean existsByNome(String nome);
}
