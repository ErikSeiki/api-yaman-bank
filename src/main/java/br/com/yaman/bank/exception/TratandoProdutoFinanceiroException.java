package br.com.yaman.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class TratandoProdutoFinanceiroException {
	
	@ExceptionHandler({ProdutoFinanceiroException.class})
	public ResponseEntity<String> trataException(Exception e) {
		log.error("Exception Tratada", e);
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
