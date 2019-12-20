package br.com.yaman.bank.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "uni")
public class Teste {
	@GetMapping (value = "teste")
	public ResponseEntity<String> getVersao() throws LitoTaForaException{
		System.out.println("Qualquer coisa.");
		throw new LitoTaForaException("Lito Ta Tratado");
		//return new ResponseEntity<String>("1.0.0" , HttpStatus.OK);
	}
}
