package br.com.yaman.bank.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
	@Query("select data_transacao, descricao, valor from transacao where data_transacao > :dataInicio and data_transacao < :dataFim and fk_produto_financeiro = :produtoFinanceiro")
	public List<Transacao> buscaExtrato(@Param("dataInicio") Date dataInicio,@Param("dataFim") Date dataFim, @Param("produtoFinanceiro") Integer produtoFinanceiro);
}
