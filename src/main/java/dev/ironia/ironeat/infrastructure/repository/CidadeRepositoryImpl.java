package dev.ironia.ironeat.infrastructure.repository;

import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.repository.CidadeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cidade> todas() {
        return entityManager.createQuery("from Cidade", Cidade.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Cidade adicionar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    @Override
    public Cidade porId(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public void remover(Cidade cidade) {
        cidade = porId(cidade.getId());
        entityManager.remove(cidade);
    }
}
