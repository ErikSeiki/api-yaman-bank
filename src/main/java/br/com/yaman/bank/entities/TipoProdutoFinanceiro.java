package br.com.yaman.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="TIPO_PRODUTO_FINANCEIRO")
public class TipoProdutoFinanceiro {
	@Id
	@Column(name = "TIPO_PRODUTO_FINANCEIRO_ID")
	private Integer tipoProdutoFinanceiroId;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
}
