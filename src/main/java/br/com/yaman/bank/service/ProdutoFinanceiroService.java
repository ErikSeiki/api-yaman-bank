package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	public String sacar(ParamSacarDTO parametro) throws ProdutoFinanceiroException {
		
		ProdutoFinanceiro produtoFinanceiro = produtoFinanceiroRepository.findById(parametro.getProdutoFinanceiroId()).orElse(null);
		
		float valorDoSaque = parametro.getValorDoSaque();
		
		if(produtoFinanceiro == null) {
			throw new ProdutoFinanceiroException("Produto financeiro invalido");
		}
		
		if(valorDoSaque <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		if(produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()	) {
			//produtoFinanceiro = this.descontarValor(produtoFinanceiro, valorDoSaque);
			this.descontarValor(produtoFinanceiro, valorDoSaque);
			produtoFinanceiroRepository.save(produtoFinanceiro);
			return MESAGEM_SUCESSO + produtoFinanceiro.getValor();
		}
		/*
		else if(produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == 2) {
			//produtoFinanceiro = this.descontarValor(produtoFinanceiro, valorDoSaque);
			this.descontarValor(produtoFinanceiro, valorDoSaque);
			produtoFinanceiroRepository.save(produtoFinanceiro);
			return MESAGEM_SUCESSO + produtoFinanceiro.getValor();
		}*/
		throw new ProdutoFinanceiroException("Tipo de conta invalido");
	}
	
	public void descontarValor(ProdutoFinanceiro produtoFinanceiro, float valorSaque) throws ProdutoFinanceiroException {
		if(produtoFinanceiro.getValor() >= valorSaque) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - valorSaque);
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	};
	
	
	public ProdutoFinanceiro buscarPoupanca(Integer numeroConta, Integer agencia) throws Exception  {
		/*
		List<ProdutoFinanceiro> produtos = this.buscarProdutosFinanceiros(numeroConta, agencia);
		for(ProdutoFinanceiro produto : produtos) {
			if (produto.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod()) {
				return produto;
			}	
		}
		
		throw new NotFoundException("Essa conta não possui uma Conta Poupança");
		*/
		
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA);
	}
	
	public ProdutoFinanceiro buscarCorrente(Integer numeroConta, Integer agencia) throws Exception {
		/*
		List<ProdutoFinanceiro> produtos = this.buscarProdutosFinanceiros(numeroConta, agencia);
		for(ProdutoFinanceiro produto : produtos) {
			if (produto.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
				return produto;
			}	
		}
		
		return null;*/
		
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE);
		
	}

	private ProdutoFinanceiro buscarProdutoFinanceiro(Integer numeroConta, Integer agencia , TipoProdutoFinanceiro tipoProdutoFinanceiro) throws NotFoundException {
		ProdutoFinanceiro produto = produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta,tipoProdutoFinanceiro.getCod());
		if(produto==null)
			throw new NotFoundException("Essa conta não possui um(a) " + tipoProdutoFinanceiro.getDescricao());

		return produto;
	}
	
}
