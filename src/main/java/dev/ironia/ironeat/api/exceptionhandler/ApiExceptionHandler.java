package dev.ironia.ironeat.api.exceptionhandler;

import dev.ironia.ironeat.domain.exception.EntidadeEmUsoException;
import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.ENTIDADE_NAO_ENCONTRADA;

        ApiError error = createErrorBuilder(httpStatus, errorType, e.getMessage()).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTIDADE_EM_USO;

        ApiError error = createErrorBuilder(httpStatus, errorType, e.getMessage()).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.NEGOCIO_EXCEPTION;

        ApiError error = createErrorBuilder(httpStatus, errorType, e.getMessage()).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null) {
            body = ApiError.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .build();
        }else if(body instanceof String) {
            body = ApiError.builder()
                    .status(status.value())
                    .title((String) body)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createErrorBuilder(HttpStatus status, ErrorType errorType, String detail){
        return ApiError.builder()
                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail);

    }
}
