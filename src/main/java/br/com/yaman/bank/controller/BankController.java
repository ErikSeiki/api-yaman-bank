package br.com.yaman.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "operacao")
public class BankController {

	@GetMapping (value = "buscar-versao")
	public ResponseEntity<String> getVersao(){
		return new ResponseEntity<String>("1.0.0" , HttpStatus.OK);
	}
	
}
