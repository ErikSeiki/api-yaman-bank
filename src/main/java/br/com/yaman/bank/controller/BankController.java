package br.com.yaman.bank.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.yaman.bank.DTO.TransacaoDTO;
import br.com.yaman.bank.business.ProdutoFinanceiroBusiness;
import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamExtratoDTO;
import br.com.yaman.bank.DTO.ParamLoginDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.DTO.ParamTransferirDTO;
import br.com.yaman.bank.DTO.ReturnDepositarDTO;
import br.com.yaman.bank.DTO.ReturnLoginDTO;
import br.com.yaman.bank.DTO.ReturnPerfilDTO;
import br.com.yaman.bank.DTO.ReturnSacarDTO;
import br.com.yaman.bank.DTO.ReturnSaldoContaCorrenteDTO;
import br.com.yaman.bank.DTO.ReturnSaldoContaPoupancaDTO;
import br.com.yaman.bank.DTO.ReturnSaldoDTO;
import br.com.yaman.bank.DTO.ReturnTransferirDTO;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;

@RestController
@RequestMapping(value = "/operacao")
public class BankController {

	@Autowired
	private ProdutoFinanceiroBusiness produtoFinanceiroBusiness;

	@GetMapping(value = "/buscar-versao")
	public ResponseEntity<String> getVersao() {
		return new ResponseEntity<String>("1.0.0", HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscar-saldo")
	public ResponseEntity<ReturnSaldoDTO> exibirSaldo(@RequestParam Integer numeroConta, Integer agencia)
			throws NotFoundException, ProdutoFinanceiroException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.buscarSaldo(numeroConta, agencia));
	}
	

	@GetMapping(value = "/buscar-saldo-poupanca")
	public ResponseEntity<ReturnSaldoContaPoupancaDTO> exibirSaldoPoupanca(@RequestParam Integer numeroConta, Integer agencia)
			throws NotFoundException, ProdutoFinanceiroException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.buscarPoupanca(numeroConta, agencia));

	}

	@GetMapping(value = "/buscar-saldo-corrente")
	public ResponseEntity<ReturnSaldoContaCorrenteDTO> exibirSaldoCorrente(@RequestParam Integer numeroConta, Integer agencia)
			throws NotFoundException, ProdutoFinanceiroException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.buscarCorrente(numeroConta, agencia));
	}

	@PostMapping(value = "/depositar")
	public ResponseEntity<ReturnDepositarDTO> depositar(@RequestBody ParamDepositarDTO parametros)
			throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.depositar(parametros));
	}

	@PostMapping(value = "/sacar")
	public ResponseEntity<ReturnSacarDTO> sacar(@RequestBody ParamSacarDTO parametros)
			throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.sacar(parametros));
	}

	@PostMapping(value = "/transferir")
	public ResponseEntity<ReturnTransferirDTO> transferir(@RequestBody ParamTransferirDTO parametros)
			throws ProdutoFinanceiroException, NotFoundException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.transferir(parametros));
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
		return ResponseEntity.ok(produtoFinanceiroBusiness.exibirExtrato(parametros));
	}

	@PostMapping(value = "/login")
	public ResponseEntity<ReturnLoginDTO> logar(@RequestParam Integer numeroConta,Integer agencia, String senha) throws Exception { 
		return ResponseEntity.ok(produtoFinanceiroBusiness.logar(new ParamLoginDTO(numeroConta, agencia, senha))); 
	}
	
	@GetMapping(value = "/perfil")
	public ResponseEntity<ReturnPerfilDTO> buscarPerfil(@RequestParam Integer numeroConta, Integer agencia)
			throws NotFoundException, ProdutoFinanceiroException {
		return ResponseEntity.ok(produtoFinanceiroBusiness.buscarPerfil(numeroConta, agencia));
	}
	
}
