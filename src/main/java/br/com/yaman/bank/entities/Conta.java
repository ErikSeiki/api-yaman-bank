package br.com.yaman.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CONTA")

public class Conta {
	
	@Id
	@Column(name = "NUMERO_CONTA")
	private Integer numeroConta;
	
	@Column(name = "AGENCIA")
	private Integer agencia;
	
	@Column(name = "SENHA")
	private String senha;
	
	
	@ManyToOne
	@JoinColumn(name = "CLIENTE_ID", referencedColumnName = "FK_CLIENTE_ID", nullable = false)
	private Cliente cliente;
	

}
