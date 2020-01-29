package br.com.yaman.bank.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	public Transacao(String descricao, Float valor, ProdutoFinanceiro produtoFinanceiro) {
		this.dataTransacao = LocalDate.now();
		this.descricao = descricao;
		this.valor = valor;
		this.produtoFinanceiro = produtoFinanceiro;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACAO_ID")
	private Integer transacaoId;
	
	@Column(name = "DATA_TRANSACAO", nullable = false)
	private LocalDate dataTransacao;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@Column(name = "VALOR", nullable = false)
	private Float valor;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "FK_PRODUTO_FINANCEIRO", referencedColumnName = "PRODUTO_FINANCEIRO_ID", nullable = false)
	private ProdutoFinanceiro produtoFinanceiro;

}
