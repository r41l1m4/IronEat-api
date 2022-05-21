package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
