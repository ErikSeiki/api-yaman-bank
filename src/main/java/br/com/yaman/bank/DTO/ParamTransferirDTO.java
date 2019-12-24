package br.com.yaman.bank.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamTransferirDTO {
	@JsonAlias("codigoProdutoMinhaConta")
	private Integer produtoFinanceiroIdMinhaConta;
	
	private Integer agenciaOutraConta;
	
	private Integer numeroOutraConta;
	
	private Integer tipoProdutoFinanceiro;
	
	private float valorDaTransferencia;
}
