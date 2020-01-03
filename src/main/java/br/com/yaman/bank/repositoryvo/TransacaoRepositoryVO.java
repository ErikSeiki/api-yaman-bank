package br.com.yaman.bank.repositoryvo;

import java.time.LocalDate;
import java.util.List;

import br.com.yaman.bank.vo.TransacaoVO;

public interface TransacaoRepositoryVO {
	
	public List<TransacaoVO> buscaExtrato(LocalDate dataInicio, LocalDate dataFim, Integer produtoFinanceiro) throws Exception;
}
