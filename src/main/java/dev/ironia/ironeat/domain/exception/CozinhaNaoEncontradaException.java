package dev.ironia.ironeat.domain.exception;

//não precisa especificar o response status, ele vai usar a da classe mãe
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("Não existe cadastro de cozinha com o código %d.", cozinhaId));
    }
}
