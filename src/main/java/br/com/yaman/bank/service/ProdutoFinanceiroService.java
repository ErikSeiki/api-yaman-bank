package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.entity.ContaCorrente;
import br.com.yaman.bank.entity.ContaPoupanca;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.entity.TipoProdutoFinanceiro;
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
		
		if(produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == 1) {
			//ContaPoupanca cp = (ContaPoupanca) produtoFinanceiro;
			produtoFinanceiro = this.discontarValor(produtoFinanceiro, valorDoSaque);
			produtoFinanceiroRepository.save(produtoFinanceiro);
			return MESAGEM_SUCESSO + produtoFinanceiro.getValor();
		}else if(produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == 2) {
			//ContaCorrente cc = (ContaCorrente) produtoFinanceiro;
			produtoFinanceiro = this.discontarValor(produtoFinanceiro, valorDoSaque);
			produtoFinanceiroRepository.save(produtoFinanceiro);
			return MESAGEM_SUCESSO + produtoFinanceiro.getValor();
		}
		throw new ProdutoFinanceiroException("Tipo de conta invalido");
	}
	
	public ProdutoFinanceiro discontarValor(ProdutoFinanceiro produtoFinanceiro, float valorSaque) throws ProdutoFinanceiroException {
		if(produtoFinanceiro.getValor() >= valorSaque) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - valorSaque);
			return produtoFinanceiro;
		}
		throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
	};
	
}
