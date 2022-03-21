package dev.ironia.ironeat.infrastructure.repository;

import dev.ironia.ironeat.domain.model.Permissao;
import dev.ironia.ironeat.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permissao> todas() {
        return entityManager.createQuery("from Permissao", Permissao.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Permissao adicionar(Permissao permissao) {
        return entityManager.merge(permissao);
    }

    @Override
    public Permissao porId(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Override
    @Transactional
    public void remover(Permissao permissao) {
        permissao = porId(permissao.getId());
        entityManager.remove(permissao);
    }
}
