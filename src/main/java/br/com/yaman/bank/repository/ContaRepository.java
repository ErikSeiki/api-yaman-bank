package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;

public interface ContaRepository extends JpaRepository<Conta, ContaPK>{

}
