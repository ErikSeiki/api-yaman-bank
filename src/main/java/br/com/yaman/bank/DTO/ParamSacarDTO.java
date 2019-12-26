package br.com.yaman.bank.DTO;

import br.com.yaman.bank.conta.TipoProdutoFinanceiro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamSacarDTO {
	private Integer numeroConta;
	private Integer agencia;
	private Integer tipoProdutoFinanceiro;
	private float valorDoSaque;
}
