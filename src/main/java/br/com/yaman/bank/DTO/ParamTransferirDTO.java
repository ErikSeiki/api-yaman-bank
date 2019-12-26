package br.com.yaman.bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamTransferirDTO {

	private Integer remetenteNumeroConta;
	private Integer remetenteAgencia;
	private Integer remetenteTipoProdutoFinanceiro;
	
	private Integer destinatarioNumeroConta;
	private Integer destinatarioAgencia;
	private Integer destinatarioTipoProdutoFinanceiro;
	
	private float valorDaTransferencia;
}
