package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yaman.bank.entity.TipoProdutoFinanceiro;

public interface TipoProdutoFinanceiroRepository extends JpaRepository<TipoProdutoFinanceiro, Integer>{

}
