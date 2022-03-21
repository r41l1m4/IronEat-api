package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> todas();
    Permissao porId(Long id);
    Permissao adicionar(Permissao permissao);
    void remover(Permissao permissao);

}
