package br.com.yaman.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.repository.ContaRepository;


@Service
public class BankService {
	
	@Autowired
	private ContaRepository contarepository;
	
	
	public List<ProdutoFinanceiro> buscarProdutosFinanceiros(Integer numeroConta, Integer agencia) throws NotFoundException {
		ContaPK id = new ContaPK(numeroConta, agencia);
		
		try {
			Conta conta = this.contarepository.findById(id).get();
			return conta.getProdutosFinanceiros();
			
		} catch (Exception e) {
			throw new NotFoundException("Conta não encontrada!");
		}
		
	}
	
	public ProdutoFinanceiro buscarPoupanca(Integer numeroConta, Integer agencia) throws Exception  {
		List<ProdutoFinanceiro> produtos = this.buscarProdutosFinanceiros(numeroConta, agencia);
		for(ProdutoFinanceiro produto : produtos) {
			if (produto.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == 1) {
				return produto;
			}	
		}
		
		throw new NotFoundException("Essa conta não possui uma Conta Poupança");
	}
	
	public ProdutoFinanceiro buscarCorrente(Integer numeroConta, Integer agencia) throws Exception {
		List<ProdutoFinanceiro> produtos = this.buscarProdutosFinanceiros(numeroConta, agencia);
		for(ProdutoFinanceiro produto : produtos) {
			if (produto.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == 2) {
				return produto;
			}	
		}
		
		return null;
		
	}
	
	
	
	

}
