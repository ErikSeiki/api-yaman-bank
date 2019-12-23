package br.com.yaman.bank.DTO;


import com.fasterxml.jackson.annotation.JsonAlias;

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
