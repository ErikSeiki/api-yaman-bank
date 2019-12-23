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
	@JsonAlias("codigoProduto")
	private Integer produtoFinanceiroId;
	private float valorDoSaque;
}
