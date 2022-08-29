package dev.ironia.ironeat.domain.model;

import dev.ironia.ironeat.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @JoinColumn(nullable = false)
    @ManyToOne
    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    private Estado estado;
}
