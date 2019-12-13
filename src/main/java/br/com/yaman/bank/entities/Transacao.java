package br.com.yaman.bank.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="TRANSACAO")
public class Transacao {
	@Id
	@Column(name = "TRANSACAO_ID")
	private Integer transacaoId;
	
	@Column(name = "DATA_TRANSACAO")
	private Date dataTransacao;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "VALOR")
	private String valor;
	
	@OneToOne
	@Column(name = "PRODUTO_FINANCEIRO")
	private ProdutoFinanceiro produtoFinanceiro;
	

}
