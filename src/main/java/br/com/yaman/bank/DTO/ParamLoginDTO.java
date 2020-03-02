package br.com.yaman.bank.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamLoginDTO {
	private Integer numeroConta;
	private Integer agencia;
	private Integer senha;	
}
