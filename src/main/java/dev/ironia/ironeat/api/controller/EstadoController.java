package dev.ironia.ironeat.api.controller;

import dev.ironia.ironeat.api.assembler.EstadoDTOAssembler;
import dev.ironia.ironeat.api.assembler.EstadoInputDTODisassembler;
import dev.ironia.ironeat.api.model.input.EstadoInputDTO;
import dev.ironia.ironeat.api.model.output.EstadoOutputDTO;
import dev.ironia.ironeat.domain.model.Estado;
import dev.ironia.ironeat.domain.repository.EstadoRepository;
import dev.ironia.ironeat.domain.service.CadastroEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
    private EstadoRepository estadoRepository;
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoInputDTODisassembler estadoDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstadoOutputDTO> listar() {
        return estadoDTOAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoOutputDTO buscar(@PathVariable Long id) {
        return estadoDTOAssembler.toModel(cadastroEstadoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoOutputDTO salvar(@RequestBody @Valid EstadoInputDTO estado) {
        return estadoDTOAssembler.toModel(cadastroEstadoService.salvar(estadoDisassembler.fromModel(estado)));
    }

    @PutMapping("/{id}")
    public EstadoOutputDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInputDTO estado) {
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(id);
        estadoDisassembler.copyToDomainObject(estado, estadoAtual);

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoDTOAssembler.toModel(cadastroEstadoService.salvar(estadoAtual));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroEstadoService.excluir(id);
    }
}

