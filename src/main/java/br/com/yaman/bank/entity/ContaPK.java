package br.com.yaman.bank.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "NUMERO_CONTA")
	private Integer numeroConta;

	@Column(name = "AGENCIA")
	private Integer agencia;
}
