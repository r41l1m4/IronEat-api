package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> todas();
    Cidade porId(Long id);
    Cidade adicionar(Cidade cidade);
    void remover(Cidade cidade);

}
