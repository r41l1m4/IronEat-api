package dev.ironia.ironeat;

import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.model.Restaurante;
import dev.ironia.ironeat.domain.repository.CozinhaRepository;
import dev.ironia.ironeat.domain.repository.RestauranteRepository;
import dev.ironia.ironeat.util.DatabaseCleaner;
import dev.ironia.ironeat.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestauranteIT {

    @LocalServerPort
    private int port;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    DatabaseCleaner databaseCleaner;

    private final int RESTAURANTE_INEXISTENTE = 1500;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        basePath = "/restaurantes";

        //databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoListarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
            .body(ResourceUtils.getContentFromResource("JSON/restaurante_cadastro_body.json"))
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCorpoVazio() {
        given()
            .body("{}")
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_INEXISTENTE)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");
        cozinhaRepository.save(cozinha);

        Restaurante res1 = new Restaurante();
        res1.setNome("Rest");
        res1.setTaxaFrete(BigDecimal.valueOf(15));
        res1.setCozinha(cozinha);

        Restaurante res2 = new Restaurante();
        res2.setNome("Aurante");
        res2.setTaxaFrete(BigDecimal.valueOf(5));
        res2.setCozinha(cozinha);

    }

}
