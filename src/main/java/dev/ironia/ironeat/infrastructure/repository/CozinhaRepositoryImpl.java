package dev.ironia.ironeat.infrastructure.repository;

import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cozinha> todas() {
        return entityManager.createQuery("from Cozinha", Cozinha.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Cozinha adicionar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    @Override
    public Cozinha porId(Long id) {
        return entityManager.find(Cozinha.class, id);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = porId(id);

        if(cozinha == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(cozinha);
    }
}
