package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.entity.TipoProdutoFinanceiro;

@Repository
public interface ProdutoFinanceiroRepository extends JpaRepository<ProdutoFinanceiro, Integer> {
	
	@Query("select p from ProdutoFinanceiro p where p.conta.contaPk.agencia = :agencia and p.conta.contaPk.numeroConta = :conta and p.tipoProdutoFinanceiro.tipoProdutoFinanceiroId = :tipoProdutoFinanceiroId")
	public ProdutoFinanceiro buscarProdutoFinanceiro(@Param("agencia") Integer agencia,@Param("conta") Integer numeroConta, @Param("tipoProdutoFinanceiroId") Integer tipoProdutoFinanceiroId);
	
	public ProdutoFinanceiro findByTipoProdutoFinanceiro(TipoProdutoFinanceiro tipoProdutoFinanceiro);
}
