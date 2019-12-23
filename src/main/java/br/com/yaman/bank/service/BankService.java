package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.repository.ProdutoFinanceiroRepository;


@Service
public class BankService {
	/*
	@Autowired
	private ContaRepository contarepository;
	*/
	@Autowired
	private ProdutoFinanceiroRepository produtoFinanceiro;
	
	/*
	private List<ProdutoFinanceiro> buscarProdutosFinanceiros(Integer numeroConta, Integer agencia) throws NotFoundException {
		ContaPK id = new ContaPK(numeroConta, agencia);
		
		try {
			Conta conta = this.contarepository.findById(id).get();
			return conta.getProdutosFinanceiros();
		} catch (Exception e) {
			throw new NotFoundException("Conta não encontrada!");
		}
		
	}*/
	
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
		ProdutoFinanceiro produto = produtoFinanceiro.buscarProdutoFinanceiro(agencia, numeroConta,tipoProdutoFinanceiro.getCod());
		if(produto==null)
			throw new NotFoundException("Essa conta não possui um(a) " + tipoProdutoFinanceiro.getDescricao());

		return produto;
	}
}
