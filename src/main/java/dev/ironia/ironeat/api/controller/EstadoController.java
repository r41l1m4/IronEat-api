package dev.ironia.ironeat.api.controller;

import dev.ironia.ironeat.domain.exception.EntidadeEmUsoException;
import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import dev.ironia.ironeat.domain.service.CadastroEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
    private EstadoRepository estadoRepository;
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);

        if(estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        Optional<Estado> estadoAtual = estadoRepository.findById(id);

        if(estadoAtual.isPresent()) {
            BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
            cadastroEstadoService.salvar(estadoAtual.get());
            return ResponseEntity.ok(estadoAtual.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            cadastroEstadoService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}

