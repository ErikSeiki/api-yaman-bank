package br.com.yaman.bank.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NUMERO_CONTA")
	private Integer numeroConta;

	@Id
	@Column(name = "AGENCIA")
	private Integer agencia;

	@Column(name = "SENHA", nullable = false)
	private String senha;

	@ManyToOne
	@JoinColumn(name = "FK_CLIENTE_ID", referencedColumnName = "CLIENTE_ID", nullable = false)
	private Cliente cliente;

	@OneToMany(mappedBy = "conta")
	private List<ProdutoFinanceiro> produtosFinanceiros;

}
