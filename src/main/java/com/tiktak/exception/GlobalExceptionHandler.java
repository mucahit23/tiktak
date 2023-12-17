package com.tiktak.exception;

import com.tiktak.dto.response.base.ErrorResponse;
import com.tiktak.dto.response.base.GenericResponse;
import com.tiktak.util.TiktakUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public GenericResponse<ErrorResponse> handleCustomException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(TiktakUtils.getFormattedMessage(ex.getStackTrace().toString()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return TiktakUtils.generateGenericErrorResponse(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public GenericResponse<ErrorResponse> handleGeneralException(BadRequestException ex) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();

        return TiktakUtils.generateGenericErrorResponse(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public GenericResponse<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.addFieldError(com.tiktak.dto.response.base.FieldError.builder().field(fieldName).errorMessage(errorMessage).build());
        });
        return TiktakUtils.generateGenericErrorResponse(errorResponse);
    }

    @ExceptionHandler(Throwable.class)
    public GenericResponse<ErrorResponse> handleThrowable(final Throwable exception) {
        exception.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(TiktakUtils.getFormattedMessage(exception.getMessage()));
        return TiktakUtils.generateGenericErrorResponse(errorResponse);
    }

}