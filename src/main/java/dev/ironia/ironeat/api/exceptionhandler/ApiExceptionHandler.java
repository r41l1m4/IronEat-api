package dev.ironia.ironeat.api.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import dev.ironia.ironeat.domain.exception.EntidadeEmUsoException;
import dev.ironia.ironeat.domain.exception.EntidadeNaoEncontradaException;
import dev.ironia.ironeat.domain.exception.NegocioException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. Tente novamente e se o " +
            "problema persistir, entre em contato como administrador do sistema.";

    private MessageSource messageSource;

    ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }else if(rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisição está inválido. Verifique erros de sintaxe.";
        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        ApiError error = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa proprieade e tente novamente.", e.getPropertyName());

        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        ApiError error = createErrorBuilder(status, errorType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        e.getPath().forEach((reference -> System.out.println(reference.getFieldName())));

        String path = e.getPath().stream()
                .map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));

        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo '%s'", path, e.getValue(), e.getTargetType().getSimpleName());
        ApiError error = createErrorBuilder(status, errorType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorType errorType = ErrorType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar é inexistente.", e.getRequestURL());
        ApiError error = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorType errorType = ErrorType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";


        List<ApiError.Field> fields = e.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return ApiError.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ApiError error = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .fields(fields)
                .build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest request) {
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo '%s'",
                e.getName(), e.getValue(), e.getRequiredType().getSimpleName());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.PARAMETRO_INVALIDO;
        ApiError error = createErrorBuilder(status, errorType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);

    }

//    @ExceptionHandler(TransactionSystemException.class)
//    private ResponseEntity<?> handleTransactionSystemException(TransactionSystemException e, WebRequest request) {
//        Throwable rootCause = ExceptionUtils.getRootCause(e);
//
//        List<ApiError.Field> fields = Arrays.stream(rootCause.getStackTrace())
//                .map(stackTraceElement -> ApiError.Field.builder()
//                        .name(stackTraceElement.getFileName())
//                        .userMessage(stackTraceElement.getMethodName())
//                        .build())
//                .collect(Collectors.toList());
//
//        String detail = String.format("O parâmetro de URL '%s'",
//                rootCause.getLocalizedMessage());
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        ErrorType errorType = ErrorType.DADOS_INVALIDOS;
//        ApiError error = createErrorBuilder(status, errorType, detail)
//                .userMessage(e.getMostSpecificCause().getMessage())
//                .fields(fields)
//                .build();
//
//        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
//
//    }
//    @ExceptionHandler(RollbackException.class)
//    private ResponseEntity<?> handleRollbackException(RollbackException e, WebRequest request) {
//        Throwable rootCause = ExceptionUtils.getRootCause(e);
//
//        List<ApiError.Field> fields = Arrays.stream(rootCause.getStackTrace())
//                .map(stackTraceElement -> ApiError.Field.builder()
//                        .name(stackTraceElement.getFileName())
//                        .userMessage(stackTraceElement.getMethodName())
//                        .build())
//                .collect(Collectors.toList());
//
//        String detail = String.format("O parâmetro de URL '%s'",
//                rootCause.getLocalizedMessage());
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        ErrorType errorType = ErrorType.DADOS_INVALIDOS;
//        ApiError error = createErrorBuilder(status, errorType, detail)
//                .userMessage(e.getLocalizedMessage())
//                .fields(fields)
//                .build();
//
//        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
//
//    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.RECURSO_NAO_ENCONTRADO;

        ApiError error = createErrorBuilder(httpStatus, errorType, e.getMessage())
                .userMessage(e.getMessage())
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTIDADE_EM_USO;

        ApiError error = createErrorBuilder(httpStatus, errorType, e.getMessage())
                .userMessage(e.getMessage())
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.ERRO_NEGOCIO;

        ApiError error = createErrorBuilder(httpStatus, errorType, e.getMessage())
                .userMessage(e.getMessage())
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.ERRO_DE_SISTEMA;
        ApiError error = createErrorBuilder(status, errorType, MSG_ERRO_GENERICA_USUARIO_FINAL)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null) {
            body = ApiError.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .timestamp(LocalDateTime.now())
                    .build();
        }else if(body instanceof String) {
            body = ApiError.builder()
                    .status(status.value())
                    .title((String) body)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createErrorBuilder(HttpStatus status, ErrorType errorType, String detail){
        return ApiError.builder()

                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }
}
