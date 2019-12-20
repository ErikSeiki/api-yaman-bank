package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.yaman.bank.entity.ContaCorrente;
import br.com.yaman.bank.entity.ContaPoupanca;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.entity.TipoProdutoFinanceiro;

@Repository
public interface ProdutoFinanceiroRepository extends JpaRepository<ProdutoFinanceiro, Integer>{
	
	public ProdutoFinanceiro findByTipoProdutoFinanceiro(TipoProdutoFinanceiro tipoProdutoFinanceiro);
}
