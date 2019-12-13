package br.com.yaman.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="TIPO_PRODUTO_FINANCEIRO")
public class TipoProdutoFinanceiro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TIPO_PRODUTO_FINANCEIRO_ID")
	private Integer tipoProdutoFinanceiroId;
	
	@Column(name = "DESCRICAO",  nullable = false)
	private String descricao;
	
}
