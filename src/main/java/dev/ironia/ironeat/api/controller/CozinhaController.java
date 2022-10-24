package dev.ironia.ironeat.api.controller;

import dev.ironia.ironeat.api.assembler.CozinhaDTOAssembler;
import dev.ironia.ironeat.api.assembler.CozinhaInputDTODisassembler;
import dev.ironia.ironeat.api.model.input.CozinhaInputDTO;
import dev.ironia.ironeat.api.model.output.CozinhaOutputDTO;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.repository.CozinhaRepository;
import dev.ironia.ironeat.domain.service.CadastroCozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
    private CozinhaRepository cozinhaRepository;
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;

    @Autowired
    private CozinhaInputDTODisassembler cozinhaDisassembler;

    @GetMapping
    public List<CozinhaOutputDTO> listar() {
        return cozinhaDTOAssembler.toCollectionModel(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaOutputDTO buscar(@PathVariable("cozinhaId") Long id) {
        return cozinhaDTOAssembler.toModel(cadastroCozinhaService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutputDTO salvar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        return cozinhaDTOAssembler.toModel(cadastroCozinhaService.salvar(cozinhaDisassembler.fromModel(cozinhaInputDTO)));
    }

    @PutMapping("/{id}")
    public CozinhaOutputDTO atualizar(@PathVariable Long id, @RequestBody CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);
        cozinhaDisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

//        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return cozinhaDTOAssembler.toModel(cadastroCozinhaService.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCozinhaService.excluir(id);
    }
}
