package br.com.yaman.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;
import br.com.yaman.bank.repository.ContaRepository;

@Service
public class ContaService {
	
	@Autowired
	ContaRepository contaRepository;

	public Optional<Conta> buscarConta(ContaPK contaPk){
		return contaRepository.findById(contaPk);
	}
	
}
