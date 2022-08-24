package dev.ironia.ironeat.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    NEGOCIO_EXCEPTION("/negocio-exception", "Negocio exception");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://ironia.dev" + path;
        this.title = title;
    }
}
