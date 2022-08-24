package dev.ironia.ironeat.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//não precisa especificar o response status, ele vai usar a da classe mãe
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de estado com o código %d.", estadoId));
    }
}
