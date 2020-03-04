package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.repository.ProdutoFinanceiroRepository;

@Service
public class ProdutoFinanceiroService {

	@Autowired
	private ProdutoFinanceiroRepository produtoFinanceiroRepository;
	
	public void alterarProdutoFinanceiro(ProdutoFinanceiro produto) {
		produtoFinanceiroRepository.save(produto);
	}
	
	public ProdutoFinanceiro buscarProdutoFinanceiroPeloIdsETipoProdutoFinanceiro(Integer agencia, Integer numeroConta, Integer tipoProdutoFinanceiro) {
		return produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
	}
}
