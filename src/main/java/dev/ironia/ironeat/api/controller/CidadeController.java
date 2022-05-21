package dev.ironia.ironeat.api.controller;

import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.repository.CidadeRepository;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import dev.ironia.ironeat.domain.service.CadastroCidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {
    private CidadeRepository cidadeRepository;
    private EstadoRepository estadoRepository;
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);

        if(cidade.isPresent()) {
            return ResponseEntity.ok(cidade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
        try{
            cidade = cadastroCidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(cidade);
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Optional<Cidade> cidadeAtual = cidadeRepository.findById(id);

        if(cidadeAtual.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
            cadastroCidadeService.salvar(cidadeAtual.get());
            return ResponseEntity.ok(cidadeAtual.get());
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try{
            cadastroCidadeService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
