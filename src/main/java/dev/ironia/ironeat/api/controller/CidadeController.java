package dev.ironia.ironeat.api.controller;

import dev.ironia.ironeat.api.assembler.CidadeDTOAssembler;
import dev.ironia.ironeat.api.assembler.CidadeInputDTODisassembler;
import dev.ironia.ironeat.api.model.input.CidadeInputDTO;
import dev.ironia.ironeat.api.model.output.CidadeOutputDTO;
import dev.ironia.ironeat.domain.exception.EstadoNaoEncontradoException;
import dev.ironia.ironeat.domain.exception.NegocioException;
import dev.ironia.ironeat.domain.model.Cidade;
import dev.ironia.ironeat.domain.repository.CidadeRepository;
import dev.ironia.ironeat.domain.service.CadastroCidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {
    private CidadeRepository cidadeRepository;
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;

    @Autowired
    private CidadeInputDTODisassembler cidadeDisassembler;

    @GetMapping
    public List<CidadeOutputDTO> listar() {
        return cidadeDTOAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeOutputDTO buscar(@PathVariable Long id) {
        return cidadeDTOAssembler.toModel(cadastroCidadeService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutputDTO salvar(@RequestBody @Valid CidadeInputDTO cidade) {
        try {
            return cidadeDTOAssembler.toModel(cadastroCidadeService.salvar(cidadeDisassembler.fromModel(cidade)));
        } catch(EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeOutputDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInputDTO cidade) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);
            cidadeDisassembler.copyToDomainObject(cidade, cidadeAtual);
//            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cidadeDTOAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
        } catch(EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cadastroCidadeService.excluir(id);
    }

}
