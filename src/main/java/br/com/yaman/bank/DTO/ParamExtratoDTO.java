package br.com.yaman.bank.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ParamExtratoDTO {
	private Integer numeroConta;
	private Integer agencia;
	private Integer tipoProdutoFinanceiro;
	private Date dataInicio;
	private Date dataFim;
}
