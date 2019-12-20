package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yaman.bank.entity.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

}
