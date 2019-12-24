package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.repository.ProdutoFinanceiroRepository;

@Service
public class ProdutoFinanceiroService {

	private static final String MESAGEM_SUCESSO =  "Saque sucedido, você possui: ";
	
	@Autowired
	private ProdutoFinanceiroRepository produtoFinanceiroRepository;
	
	
	public String sacar(ParamSacarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		float valorDoSaque = parametros.getValorDoSaque();
		
		ProdutoFinanceiro produto = produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
		
		if(valorDoSaque <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		else {
			this.descontarValor(produto, valorDoSaque);
			produtoFinanceiroRepository.save(produto);
			return MESAGEM_SUCESSO + produto.getValor();
		
		}
	}
	
	private void descontarValor(ProdutoFinanceiro produtoFinanceiro, float valorSaque) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorSaque) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - valorSaque);
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	public ProdutoFinanceiro buscarPoupanca(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException   {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA.getCod());
	}
	
	public ProdutoFinanceiro buscarCorrente(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException  {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE.getCod());
	}

	private ProdutoFinanceiro buscarProdutoFinanceiro(Integer numeroConta, Integer agencia , Integer tipoProdutoFinanceiro) throws NotFoundException, ProdutoFinanceiroException {
		
		if(numeroConta == null || agencia == null || tipoProdutoFinanceiro == null) {
			throw new NotFoundException("Informações de conta ou de Tipo Produto Financeiro inválidos.");
		}
		
		if (tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
			ProdutoFinanceiro produto = produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
			return produto;
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
		
		
	}

	public String depositar(ParamDepositarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		float valorDoDeposito = parametros.getValorDoDeposito();
		
		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(numeroConta, agencia, tipoProdutoFinanceiro);
		
		if(valorDoDeposito <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		produto.setValor(produto.getValor() + valorDoDeposito);
		produtoFinanceiroRepository.save(produto);
		return "Deposito sucedido! Você possui: " + produto.getValor();
	}
	
}
