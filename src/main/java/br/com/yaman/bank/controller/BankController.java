package br.com.yaman.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.service.ProdutoFinanceiroService;

@RestController
@RequestMapping(value = "operacao")
public class BankController {
	@Autowired
	private ProdutoFinanceiroService produtoFinanceiroService;

	@GetMapping (value = "buscar-versao")
	public ResponseEntity<String> getVersao(){
		return new ResponseEntity<String>("1.0.0" , HttpStatus.OK);
	}
	
	@PostMapping (value = "sacar")
	public ResponseEntity<String> sacar(@RequestBody ParamSacarDTO parametros) throws ProdutoFinanceiroException{
		return ResponseEntity.ok(produtoFinanceiroService.sacar(parametros));
	}
}