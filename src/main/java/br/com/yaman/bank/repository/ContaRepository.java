package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;

@Repository
public interface ContaRepository extends JpaRepository<Conta, ContaPK>{

}
