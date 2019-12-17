package br.com.yaman.bank.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACAO_ID")
	private Integer transacaoId;

	@Column(name = "DATA_TRANSACAO", nullable = false)
	private Date dataTransacao;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@Column(name = "VALOR", nullable = false)
	private Float valor;
	
	@ManyToOne
	@JoinColumn(name = "FK_PRODUTO_FINANCEIRO_ID", referencedColumnName = "PRODUTO_FINANCEIRO_ID", nullable = false)
	private ProdutoFinanceiro produtoFinanceiro;

}
