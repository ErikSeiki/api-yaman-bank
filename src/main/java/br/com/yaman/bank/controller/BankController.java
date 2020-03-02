package br.com.yaman.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yaman.bank.DTO.TransacaoDTO;
import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamExtratoDTO;
import br.com.yaman.bank.DTO.ParamLoginDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.DTO.ParamTransferirDTO;
import br.com.yaman.bank.DTO.ReturnSaldoContaCorrenteDTO;
import br.com.yaman.bank.DTO.ReturnSaldoContaPoupancaDTO;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.service.ProdutoFinanceiroService;

@RestController
@RequestMapping(value = "/operacao")
public class BankController {

	@Autowired
	private ProdutoFinanceiroService produtoFinanceiroService;

	@GetMapping(value = "/buscar-versao")
	public ResponseEntity<String> getVersao() {
		return new ResponseEntity<String>("1.0.0", HttpStatus.OK);
	}

	@GetMapping(value = "/buscar-saldo-poupanca")
	public ResponseEntity<ReturnSaldoContaPoupancaDTO> exibirSaldoPoupanca(@RequestParam Integer numeroConta, Integer agencia)
			throws NotFoundException, ProdutoFinanceiroException {
		ProdutoFinanceiro produto = produtoFinanceiroService.buscarPoupanca(numeroConta, agencia);
		ReturnSaldoContaPoupancaDTO returnSaldo = new ReturnSaldoContaPoupancaDTO(produto.getValor()); 
		return ResponseEntity.ok(returnSaldo);

	}

	@GetMapping(value = "/buscar-saldo-corrente")
	public ResponseEntity<ReturnSaldoContaCorrenteDTO> exibirSaldoCorrente(@RequestParam Integer numeroConta, Integer agencia)
			throws NotFoundException, ProdutoFinanceiroException {
		ProdutoFinanceiro produto = produtoFinanceiroService.buscarCorrente(numeroConta, agencia);
		ReturnSaldoContaCorrenteDTO returnSaldo = new ReturnSaldoContaCorrenteDTO(produto.getValor()); 
		return ResponseEntity.ok(returnSaldo);
	}

	@PostMapping(value = "/depositar", consumes= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> depositar(@RequestBody ParamDepositarDTO parametros)
			throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroService.depositar(parametros));
	}

	@PostMapping(value = "/sacar")
	public ResponseEntity<String> sacar(@RequestBody ParamSacarDTO parametros)
			throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroService.sacar(parametros));
	}

	@PostMapping(value = "/transferir")
	public ResponseEntity<String> transferir(@RequestBody ParamTransferirDTO parametros)
			throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroService.transferir(parametros));
	}

	/*
	 * O metodo GET não deve usar corpo na sua requisição, o HTTP permite SIM que
	 * qualquer requisição tenha um corpo, mas não é boa pratica fazer isso com GET
	 * O get tem que ser identificado pela sua URI logo os parametros de busca tem
	 * que ser passados por ela Poderiamos ter problema por exemplo para cachear
	 * informações Os proxies não procurarão no corpo GET para ver se os parâmetros
	 * têm impacto na resposta Como falei no primeiro topico do comentario, nada
	 * impede passar um corpo http no GET, e como esse corpo é usado apenas para
	 * filtro e NÃO para alterar o estado do servidor, vamos deixar como esta.
	 */
	@GetMapping(value = "/exibir-extrato")
	public ResponseEntity<List<TransacaoDTO>> exibirExtrato(@RequestBody ParamExtratoDTO parametros) throws Exception {
		return ResponseEntity.ok(produtoFinanceiroService.exibirExtrato(parametros));
	}

	@PostMapping(value = "/login") public ResponseEntity<String>
	logar(@RequestBody ParamLoginDTO parametros) throws Exception { 
		return ResponseEntity.ok(produtoFinanceiroService.logar(parametros)); 
	}

}
