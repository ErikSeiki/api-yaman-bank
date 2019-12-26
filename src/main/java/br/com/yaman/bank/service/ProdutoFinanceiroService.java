package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.DTO.ParamTransferirDTO;
import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.repository.ContaRepository;
import br.com.yaman.bank.repository.ProdutoFinanceiroRepository;

@Service
public class ProdutoFinanceiroService {

	private static final String MESAGEM_SUCESSO =  "Saque sucedido, você possui: ";
	
	@Autowired
	private ProdutoFinanceiroRepository produtoFinanceiroRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	public String sacar(ParamSacarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		float valorDoSaque = parametros.getValorDoSaque();
		
		if(valorDoSaque <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}

		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(numeroConta , agencia, tipoProdutoFinanceiro);
		
		this.descontarValor(produto, valorDoSaque);
		produtoFinanceiroRepository.save(produto);
		return MESAGEM_SUCESSO + produto.getValor();
		
	}
	
	public String transferir(ParamTransferirDTO parametros) throws ProdutoFinanceiroException {
		ProdutoFinanceiro produtoFinanceiroMinhaConta = produtoFinanceiroRepository.findById(parametros.getProdutoFinanceiroIdMinhaConta()).orElse(null);
		ProdutoFinanceiro produtoFinanceiroOutraConta =  produtoFinanceiroRepository.buscarProdutoFinanceiro(parametros.getAgenciaOutraConta(), parametros.getNumeroOutraConta(), parametros.getTipoProdutoFinanceiro());
		
		float valorDaTransferencia = parametros.getValorDaTransferencia();
		
		if(produtoFinanceiroMinhaConta == null) {
			throw new ProdutoFinanceiroException("Remetente do produto financeiro invalido");
		}
		
		if(produtoFinanceiroOutraConta == null) {
			throw new ProdutoFinanceiroException("Destinatario do produto financeiro invalido");
		}
		
		if(valorDaTransferencia <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		if(produtoFinanceiroMinhaConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				produtoFinanceiroMinhaConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod() && 
				produtoFinanceiroOutraConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
						produtoFinanceiroOutraConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
		
			this.descontarValorComJuros(produtoFinanceiroMinhaConta, valorDaTransferencia);
			this.acrescentarValor(produtoFinanceiroOutraConta, valorDaTransferencia );
			produtoFinanceiroRepository.save(produtoFinanceiroMinhaConta);
			produtoFinanceiroRepository.save(produtoFinanceiroOutraConta);
			return MENSAGEM_SUCESSO_TRANSFERENCIA + produtoFinanceiroMinhaConta.getValor();
		
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
	}
	
	public void descontarValorComJuros(ProdutoFinanceiro produtoFinanceiro, float valorDaTransferencia) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorDaTransferencia) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - (valorDaTransferencia * 1.1f));
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	private void descontarValor(ProdutoFinanceiro produtoFinanceiro, float valorSaque) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorSaque) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - valorSaque);
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	public void acrescentarValor(ProdutoFinanceiro produtoFinanceiro, float valorDaTransferencia){
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() +valorDaTransferencia);
	}
	
	public ProdutoFinanceiro buscarPoupanca(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException   {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA.getCod());
	}
	
	public ProdutoFinanceiro buscarCorrente(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException  {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE.getCod());
	}

	public String depositar(ParamDepositarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		float valorDoDeposito = parametros.getValorDoDeposito();
		
		//Validações em primeiro lugar, assim evita processamento desnecessario
		if(valorDoDeposito <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}

		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(numeroConta, agencia, tipoProdutoFinanceiro);
		
		produto.setValor(produto.getValor() + valorDoDeposito);
		produtoFinanceiroRepository.save(produto);
		return "Deposito sucedido! Você possui: " + produto.getValor();
	}


	
	private ProdutoFinanceiro buscarProdutoFinanceiro(Integer numeroConta, Integer agencia , Integer tipoProdutoFinanceiro) throws NotFoundException, ProdutoFinanceiroException {
		
		if(numeroConta == null || agencia == null || tipoProdutoFinanceiro == null) {
			throw new NotFoundException("Informações de conta ou de Tipo Produto Financeiro inválidos.");
		}
		
		if (tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
			
			ProdutoFinanceiro produto = produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
			
			if(produto == null)
				throw new NotFoundException("Não foi localizado produto financeiro para a agencia: [" + agencia + "] e conta: [" + numeroConta + "] ");
			
			return produto;
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
		
		
	}
}
