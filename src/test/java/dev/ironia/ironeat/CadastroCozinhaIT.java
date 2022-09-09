package dev.ironia.ironeat;

import dev.ironia.ironeat.domain.exception.EntidadeEmUsoException;
import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Restaurante;
import dev.ironia.ironeat.domain.service.CadastroCozinhaService;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.ironia.ironeat.domain.service.CadastroRestauranteService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
        //cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //ação
        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();

    }

    @Test()
    public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
        final Cozinha novaCozinha = new Cozinha();

        assertThrows(ConstraintViolationException.class, () ->{
            novaCozinha.setNome(null);
            cadastroCozinhaService.salvar(novaCozinha);
        });
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        final Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Brasileira");
        cadastroCozinhaService.salvar(novaCozinha);

        Restaurante novoRestaurante = new Restaurante();
        novoRestaurante.setCozinha(novaCozinha);
        novoRestaurante.setNome("Novo Restaurante");
        novoRestaurante.setTaxaFrete(BigDecimal.valueOf(5));
        cadastroRestauranteService.salvar(novoRestaurante);

        assertThrows(
                EntidadeEmUsoException.class,
                () -> cadastroCozinhaService.excluir(novaCozinha.getId())
        );
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroCozinhaService.excluir(30L)
        );
    }

    //############
    //API Tests
    //###########

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured
            .given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter3Cozinhas_QuandoConsultarCozinhas() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured
            .given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .body("", Matchers.hasSize(3))
                .body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
    }
}
