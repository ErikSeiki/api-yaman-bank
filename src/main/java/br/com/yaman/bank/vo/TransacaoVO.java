package br.com.yaman.bank.vo;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoVO {

	private Integer transacaoId;
	
	private Date dataTransacao;

	private String descricao;

	private Float valor;
	
	private Integer produtoFinanceiroId;

}
