package br.com.yaman.bank.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.service.BankService;

@RestController
@RequestMapping(value = "operacao")
public class BankController {
	
	@Autowired
	private BankService bankservice;

	@GetMapping (value = "buscar-versao")
	public ResponseEntity<String> getVersao(){
		return new ResponseEntity<String>("1.0.0" , HttpStatus.OK);
	}
	
	@GetMapping(value = "buscar-saldo-poupanca")
	public ResponseEntity<Float> exibirSaldoPoupanca(@RequestParam Integer numeroConta, Integer agencia) throws Exception{
		ProdutoFinanceiro produto = bankservice.buscarPoupanca(numeroConta, agencia);
		return ResponseEntity.ok(produto.getValor());
		
	}
	
	@GetMapping(value = "buscar-saldo-corrente")
	public ResponseEntity<Float> exibirSaldoCorrente(@RequestParam Integer numeroConta, Integer agencia) throws Exception{
		ProdutoFinanceiro produto = bankservice.buscarCorrente(numeroConta, agencia);
		return ResponseEntity.ok(produto.getValor());
	}
	
}

