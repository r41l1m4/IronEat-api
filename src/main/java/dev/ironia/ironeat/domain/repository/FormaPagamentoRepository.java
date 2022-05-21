package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
