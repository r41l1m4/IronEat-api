package dev.ironia.ironeat.domain.repository;

import dev.ironia.ironeat.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    //@Query("from Restaurante r join r.cozinha left join fetch r.formasPagamento") //faz join para que faça apenas uma consulta para obter todos os dados necessários.
                                                                                    //porém faz produto cartesiano das tabelas, gerando mais linhas no banco, mas o jpa resolve a
                                                                                    // representação.
    @Query("from Restaurante r join fetch r.cozinha")
    List<Restaurante> findAll();

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

//    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    // a query está sendo definida no orm.xml, mas também pode ser definida com a instrução acima.
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);

}
