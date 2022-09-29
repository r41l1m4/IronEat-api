package dev.ironia.ironeat.domain.model;

import dev.ironia.ironeat.core.validation.Groups;
import dev.ironia.ironeat.core.validation.Multiplo;
import dev.ironia.ironeat.core.validation.TaxaFrete;
import dev.ironia.ironeat.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
public class Restaurante{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
//    @NotEmpty
    @NotBlank(message = "Nome é obrigatório!")
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    @NotNull
    @Multiplo(numero = 5)
    @TaxaFrete
    private BigDecimal taxaFrete;

    @ManyToOne() //muda o fetch type de eager para lazy, ou seja, so faz a busca desses dados caso necessário.
    @JoinColumn(name = "cozinha_id", nullable = false) //se deixar sem essa linha, ele vai gerar o nome.
    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
}
