package com.weatherapi.weatherforecast.exception;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.weatherapi.weatherforecast.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDTO handleGenericException(HttpServletRequest request, Exception exception) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setTimestamp(new Date());
		errorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorDTO.setPath(request.getServletPath());
		errorDTO.addError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	
		LOGGER.error(exception.getMessage(), exception);
		return errorDTO;
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO handleBadRequestException(HttpServletRequest request, Exception exception) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setTimestamp(new Date());
		errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDTO.setPath(request.getServletPath());
		errorDTO.addError(HttpStatus.BAD_REQUEST.getReasonPhrase());
	
		LOGGER.error(exception.getMessage(), exception);
		return errorDTO;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
		ErrorDTO errorDTO = new ErrorDTO();
		
		errorDTO.setTimestamp(new Date());
		errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDTO.setPath(((ServletWebRequest) request).getRequest().getServletPath());
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		fieldErrors.forEach(fieldError -> {
			errorDTO.addError(fieldError.getDefaultMessage());
		});
		return new ResponseEntity<>(errorDTO, headers, status);
	}
	
	

}
