package br.com.yaman.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yaman.bank.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
