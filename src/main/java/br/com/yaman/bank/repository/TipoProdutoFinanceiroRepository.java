package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.yaman.bank.entity.TipoProdutoFinanceiro;

@Repository
public interface TipoProdutoFinanceiroRepository extends JpaRepository<TipoProdutoFinanceiro, Integer>{

}
