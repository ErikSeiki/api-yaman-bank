package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yaman.bank.entity.ProdutoFinanceiro;

public interface ProdutoFinanceiroRepository extends JpaRepository<ProdutoFinanceiro, Integer> {

}
