package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
