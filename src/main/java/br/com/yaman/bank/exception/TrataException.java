package br.com.yaman.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class TrataException {
	
	@ExceptionHandler({LitoTaForaException.class})
	public ResponseEntity<String> trataException() {
		log.error("Exception Tratada");
		System.out.println("Exception Tratada");
		return ResponseEntity.badRequest().body("Ajuda o lito que ta doente");
	}
}
