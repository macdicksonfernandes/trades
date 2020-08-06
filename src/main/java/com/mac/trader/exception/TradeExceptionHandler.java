package com.mac.trader.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

/***
 * Exception Handler Class for TradeException
 * @author Mac
 *
 */
@ControllerAdvice
public class TradeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TradeException.class)
    public ResponseEntity<Object> handleTradeException(
    		TradeException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("code", ex.getCode());
        body.put("description", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
}
