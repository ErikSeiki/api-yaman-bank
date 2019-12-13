package br.com.yaman.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PRODUTO_FINANCEIRO")
public class ProdutoFinanceiro {
	@Id
	@Column(name = "PRODUTO_FINANCEIRO_ID")
	private Integer produtoFinanceiroId;
	
	@Column(name = "VALOR")
	private float valor;
	
	@ManyToOne
    @JoinColumns(value = {
        @JoinColumn(name = "NUMERO_CONTA", referencedColumnName = "FK_NUMERO_CONTA"),
    @JoinColumn(name = "AGENCIA", referencedColumnName = "FK_AGENCIA")})
    private Conta conta;
	
	@OneToOne
	@Column(name="FK_TIPO_PRODUTO_FINANCEIRO_ID")
	private TipoProdutoFinanceiro tipoProdutoFinanceiro;
}
