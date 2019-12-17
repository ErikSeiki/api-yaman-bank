package br.com.yaman.bank.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUTO_FINANCEIRO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoFinanceiro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUTO_FINANCEIRO_ID")
	private Integer produtoFinanceiroId;

	@Column(name = "VALOR")
	private float valor;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "FK_AGENCIA", referencedColumnName = "AGENCIA", nullable = false),
			@JoinColumn(name = "FK_NUMERO_CONTA", referencedColumnName = "NUMERO_CONTA", nullable = false) })
	private Conta conta;

	@ManyToOne
	@JoinColumn(name = "FK_TIPO_PRODUTO_FINANCEIRO_ID", referencedColumnName = "TIPO_PRODUTO_FINANCEIRO_ID", nullable = false)
	private TipoProdutoFinanceiro tipoProdutoFinanceiro;
	
	@OneToMany(mappedBy = "produtoFinanceiro")
	private List<Transacao> transacoes;
}
