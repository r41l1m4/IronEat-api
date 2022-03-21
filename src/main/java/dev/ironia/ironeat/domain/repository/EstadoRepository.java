package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> todas();
    Estado porId(Long id);
    Estado adicionar(Estado estado);
    void remover(Estado estado);

}
