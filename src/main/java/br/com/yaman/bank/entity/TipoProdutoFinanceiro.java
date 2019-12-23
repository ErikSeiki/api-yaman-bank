package br.com.yaman.bank.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TIPO_PRODUTO_FINANCEIRO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProdutoFinanceiro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TIPO_PRODUTO_FINANCEIRO_ID")
	private Integer tipoProdutoFinanceiroId;
	
	@Column(name = "DESCRICAO",  nullable = false)
	private String descricao;
	
	@OneToMany(mappedBy = "tipoProdutoFinanceiro")
	private List<ProdutoFinanceiro> produtosFinanceiros;
}
