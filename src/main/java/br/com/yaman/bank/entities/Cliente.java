package br.com.yaman.bank.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTE")

public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENTE_ID")
	private Integer clienteId;
	
	@Column(name = "NOME",  nullable = false)
	private String nome;
	
	@Column(name = "CPF",  nullable = false)
	private String cpf;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ENDERECO",  nullable = false)
	private String endereco;
	
	@OneToMany(mappedBy = "client")
	private List<Conta> contas;
}
