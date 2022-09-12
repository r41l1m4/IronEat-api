package dev.ironia.ironeat;

import dev.ironia.ironeat.domain.model.Cozinha;
import dev.ironia.ironeat.domain.repository.CozinhaRepository;
import dev.ironia.ironeat.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;

//############
//API Tests
//###########
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter3Cozinhas_QuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(3));
//            .body("nome", Matchers.hasItems("Indiana", "Tailandesa"))
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
            .body("{ \"nome\": \"Chinesa\" }")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetonarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", 3)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", Matchers.equalTo("Americana"));
    }
    @Test
    public void deveRetonarRespostaEStatus404_QuandoConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", 4)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Brasileira");
        cozinhaRepository.save(cozinha2);

        Cozinha cozinha3 = new Cozinha();
        cozinha3.setNome("Americana");
        cozinhaRepository.save(cozinha3);
    }
}
