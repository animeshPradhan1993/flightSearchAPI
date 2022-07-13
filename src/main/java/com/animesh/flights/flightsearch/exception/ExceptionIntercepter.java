package com.animesh.flights.flightsearch.exception;

import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Aspect
public class ExceptionIntercepter {

	@AfterThrowing(value = "execution(* *(..)) &&  within(com.animesh.flights.flightsearch..controller..*))", throwing = "ex")
	public ResponseEntity<?> handleAllExceptions(Exception ex) {
		System.out.println("Inside Advice");
		if (ex instanceof BadRequestException) {
			System.out.println(ex.getMessage());
			return createBadRequestException(new BadRequestException(ex.getMessage()));
		}

		else {
			return globleExcpetionHandler(ex);
		}
	}

	public ResponseEntity<ErrorResponse> globleExcpetionHandler(Exception ex) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				ex.getMessage(), null);
		return new ResponseEntity<ErrorResponse>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<ErrorResponse> createBadRequestException(BadRequestException ex) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.toString(), ex.getMessage(),
				null);
		return new ResponseEntity<ErrorResponse>(errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}
}
