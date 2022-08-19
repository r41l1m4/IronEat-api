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
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cadastroCidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody Cidade cidade) {
        return cadastroCidadeService.salvar(cidade);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cadastroCidadeService.salvar(cidadeAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cadastroCidadeService.excluir(id);
    }
}
