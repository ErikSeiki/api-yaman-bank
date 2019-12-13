package br.com.yaman.bank.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACAO")
public class Transacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACAO_ID")
	private Integer transacaoId;

	@Column(name = "DATA_TRANSACAO", nullable = false)
	private Date dataTransacao;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@Column(name = "VALOR", nullable = false)

	@ManyToOne
	@JoinColumn(name = "PRODUTO_FINANCEIRO_ID", referencedColumnName = "FK_PRODUTO_FINANCEIRO_ID", nullable = false)
	private ProdutoFinanceiro produtoFinanceiro;

}
