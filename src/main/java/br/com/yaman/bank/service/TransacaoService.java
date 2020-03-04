package br.com.yaman.bank.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.entity.Transacao;
import br.com.yaman.bank.repository.TransacaoRepository;

@Service
public class TransacaoService {
	
	@Autowired
	TransacaoRepository transacaoRepository;
	
	public void salvarTransacao(Transacao transacao) {
		transacaoRepository.save(transacao);
	}
	
	public List<Transacao> buscaExtrato( LocalDate dataInicio, LocalDate dataFim,  Integer produtoFinanceiro){
		return transacaoRepository.buscaExtrato(dataInicio, dataFim, produtoFinanceiro);
	}
}
