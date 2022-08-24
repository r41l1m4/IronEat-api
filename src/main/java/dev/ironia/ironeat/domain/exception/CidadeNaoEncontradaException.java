package dev.ironia.ironeat.domain.exception;

//não precisa especificar o response status, ele vai usar a da classe mãe
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long cidadeId) {
        this(String.format("Não existe cadastro de cidade com o código %d.", cidadeId));
    }
}
