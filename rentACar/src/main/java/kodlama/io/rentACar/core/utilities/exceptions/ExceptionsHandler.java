package kodlama.io.rentACar.core.utilities.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.NoSuchElementException;

@RestControllerAdvice // For Exceptions
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ProblemDetails handleBrandNotFoundException(BrandNotFoundException brandNotFoundException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(brandNotFoundException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ProblemDetails handleCarNotFoundException(CarNotFoundException carNotFoundException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(carNotFoundException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ProblemDetails handleModelNotFoundException(ModelNotFoundException modelNotFoundException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(modelNotFoundException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBusinessException(BusinessException businessException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(businessException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleEmptyResultDataAccessException(EmptyResultDataAccessException emptyResultDataAccessException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage("No data matching the entered value was found!");

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(noSuchElementException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(httpMessageNotReadableException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setMessage("VALIDATION.EXCEPTION");
        validationProblemDetails.setValidationErrors(new HashMap<>());

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validationProblemDetails.getValidationErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return validationProblemDetails;
    }

}
