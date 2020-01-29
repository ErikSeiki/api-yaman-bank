package br.com.yaman.bank.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.yaman.bank.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
	@Query("select t from Transacao t where t.dataTransacao >= :dataInicio and t.dataTransacao <= :dataFim and t.produtoFinanceiro.produtoFinanceiroId = :produtoFinanceiro")
	public List<Transacao> buscaExtrato(@Param("dataInicio") LocalDate dataInicio,@Param("dataFim") LocalDate dataFim, @Param("produtoFinanceiro") Integer produtoFinanceiro);
}
