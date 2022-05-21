package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
