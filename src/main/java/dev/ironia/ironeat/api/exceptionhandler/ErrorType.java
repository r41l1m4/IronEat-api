package dev.ironia.ironeat.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://ironia.dev" + path;
        this.title = title;
    }
}
