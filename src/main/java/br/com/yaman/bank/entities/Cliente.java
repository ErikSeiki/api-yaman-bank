package br.com.yaman.bank.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CLIENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENTE_ID")
	private Integer clienteId;
	
	@Column(name = "NOME",  nullable = false)
	private String nome;
	
	@Column(name = "CPF",  nullable = false)
	private String cpf;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ENDERECO",  nullable = false)
	private String endereco;
	
	@OneToMany(mappedBy = "cliente")
	private List<Conta> contas;
}
