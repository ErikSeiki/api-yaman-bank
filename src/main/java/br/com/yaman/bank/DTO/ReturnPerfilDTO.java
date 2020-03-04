package br.com.yaman.bank.DTO;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.yaman.bank.entity.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPerfilDTO {
	private Integer clienteId;
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String endereco; 
}
