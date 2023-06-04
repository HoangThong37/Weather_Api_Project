package com.weatherapi.weatherforecast.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.weatherapi.weatherforecast.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDTO handleGenericException(HttpServletRequest request, Exception exception) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setTimestamp(new Date());
		errorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorDTO.setPath(request.getServletPath());
		errorDTO.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	
		LOGGER.error(exception.getMessage(), exception);
		return errorDTO;
	}

}
