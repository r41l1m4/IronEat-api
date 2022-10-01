package dev.ironia.ironeat.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ironia.ironeat.api.model.CozinhaDTO;
import dev.ironia.ironeat.api.model.input.CozinhaIdInputDTO;
import dev.ironia.ironeat.api.model.input.RestauranteInputDTO;
import dev.ironia.ironeat.api.model.output.RestauranteOutputDTO;
import dev.ironia.ironeat.core.validation.ValidacaoException;
import dev.ironia.ironeat.domain.exception.CozinhaNaoEncontradaException;
import dev.ironia.ironeat.domain.exception.NegocioException;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Restaurante;
import dev.ironia.ironeat.domain.repository.RestauranteRepository;
import dev.ironia.ironeat.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    private RestauranteRepository restauranteRepository;
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteOutputDTO> listar() {
        return toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteOutputDTO buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutputDTO salvar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
        try {
            return toModel(cadastroRestauranteService.salvar(fromModel(restauranteInput)));
        }catch(CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteOutputDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        Restaurante restaurante = fromModel(restauranteInputDTO);
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);

        BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro");
        try {
            return toModel(cadastroRestauranteService.salvar(restauranteAtual));
        }catch(CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}")
    public RestauranteOutputDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");
        RestauranteInputDTO restauranteInputDTO = fromOutputDTOToInputDTO(toModel(restauranteAtual));
        return atualizar(id, restauranteInputDTO);
    }

    private void validate(Restaurante restauranteAtual, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual, objectName);

        validator.validate(restauranteAtual, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            Restaurante restauranteOrigem = mapper.convertValue(camposOrigem, Restaurante.class);

            camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                System.out.println(nomePropriedade + " = " + valorPropriedade);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void remover(@PathVariable Long id) {
        cadastroRestauranteService.excluir(id);
    }

    private static RestauranteOutputDTO toModel(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteOutputDTO restauranteOutputDTO = new RestauranteOutputDTO();
        restauranteOutputDTO.setId(restaurante.getId());
        restauranteOutputDTO.setNome(restaurante.getNome());
        restauranteOutputDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteOutputDTO.setCozinha(cozinhaDTO);
        return restauranteOutputDTO;
    }
    
    private static RestauranteInputDTO fromOutputDTOToInputDTO(RestauranteOutputDTO restauranteOutputDTO) {
        CozinhaIdInputDTO cozinhaIdInputDTO = new CozinhaIdInputDTO();
        cozinhaIdInputDTO.setId(restauranteOutputDTO.getCozinha().getId());

        RestauranteInputDTO restauranteInputDTO = new RestauranteInputDTO();
        restauranteInputDTO.setNome(restauranteOutputDTO.getNome());
        restauranteInputDTO.setTaxaFrete(restauranteOutputDTO.getTaxaFrete());
        restauranteInputDTO.setCozinha(cozinhaIdInputDTO);

        return restauranteInputDTO;
    }

    private static Restaurante fromModel(RestauranteInputDTO restauranteInputDTO) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInputDTO.getNome());
        restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInputDTO.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return  restaurante;
    }

    private List<RestauranteOutputDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(RestauranteController::toModel)
                .collect(Collectors.toList());
    }
}
