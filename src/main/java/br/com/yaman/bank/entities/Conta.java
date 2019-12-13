package br.com.yaman.bank.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CONTA")

public class Conta {

	@Id
	@Column(name = "NUMERO_CONTA")
	private Integer numeroConta;

	@Id
	@Column(name = "AGENCIA")
	private Integer agencia;

	@Column(name = "SENHA", nullable = false)
	private String senha;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_ID", referencedColumnName = "FK_CLIENTE_ID", nullable = false)
	private Cliente cliente;

	@OneToMany(mappedBy = "conta")
	private List<ProdutoFinanceiro> produtosFinanceiros;

}
