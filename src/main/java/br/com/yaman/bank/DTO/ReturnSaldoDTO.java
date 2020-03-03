package br.com.yaman.bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnSaldoDTO {
	private Float valorContaCorrente;
	private Float valorContaPoupanca;
}
