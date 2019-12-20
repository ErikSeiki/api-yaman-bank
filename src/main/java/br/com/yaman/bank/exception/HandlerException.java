package br.com.yaman.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class HandlerException {

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<String> NotFoundHandler() {
		log.error("Não encontrado!");
		return ResponseEntity.badRequest().body("Conta não encontrada!");
	}
}
