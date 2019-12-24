package br.com.yaman.bank.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
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
	
	@GetMapping(value = "buscar-saldo-poupanca")
	public ResponseEntity<Float> exibirSaldoPoupanca(@RequestParam Integer numeroConta, Integer agencia) throws NotFoundException {
		ProdutoFinanceiro produto = produtoFinanceiroService.buscarPoupanca(numeroConta, agencia);
		return ResponseEntity.ok(produto.getValor());
		
	}
	
	@GetMapping(value = "buscar-saldo-corrente")
	public ResponseEntity<Float> exibirSaldoCorrente(@RequestParam Integer numeroConta, Integer agencia) throws NotFoundException {
		ProdutoFinanceiro produto = produtoFinanceiroService.buscarCorrente(numeroConta, agencia);
		return ResponseEntity.ok(produto.getValor());
	}
	
	@PostMapping(value = "depositar")
	public ResponseEntity<String> depositar(@RequestBody ParamDepositarDTO parametros) throws ProdutoFinanceiroException, NotFoundException  {
		return ResponseEntity.ok(produtoFinanceiroService.depositar(parametros));
	}
	
	@PostMapping (value = "sacar")
	public ResponseEntity<String> sacar(@RequestBody ParamSacarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroService.sacar(parametros));
	}
}

