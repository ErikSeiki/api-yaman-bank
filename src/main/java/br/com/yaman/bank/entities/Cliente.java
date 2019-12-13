package br.com.yaman.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTE")

public class Cliente {
	@Id
	@Column(name = "CLIENTE_ID")
	private Integer clienteId;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "CPF")
	private String cpf;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ENDERECO")
	private String endereco;
}
