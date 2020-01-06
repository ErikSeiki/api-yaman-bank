package br.com.yaman.bank.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import br.com.yaman.bank.DTO.TransacaoDTO;
import br.com.yaman.bank.entity.Transacao;

@Component
public class ExtratoMapper {
	
	public List<TransacaoDTO> mapear(List<Transacao> lista) {
		List<TransacaoDTO> listaExtratoMapper = new ArrayList<TransacaoDTO>();
		
		 lista.forEach(transacao ->  { 
			TransacaoDTO extratoMapper = new TransacaoDTO();
			extratoMapper.setData(transacao.getDataTransacao());
			extratoMapper.setDescricao(transacao.getDescricao());
			extratoMapper.setValor(transacao.getValor());
			listaExtratoMapper.add(extratoMapper); 
			} 
		 );
		
		 /*
		 * for(Transacao transacao : lista) { ExtratoDTO extratoMapper = new
		 * ExtratoDTO(); extratoMapper.setData(transacao.getDataTransacao());
		 * extratoMapper.setDescricao(transacao.getDescricao());
		 * extratoMapper.setValor(transacao.getValor());
		 * listaExtratoMapper.add(extratoMapper); }
		 */
		return listaExtratoMapper;
	}
	
	
	public List<TransacaoDTO> mapearComStream(List<Transacao> lista) {
		return lista.stream()
				.map(transacao ->  this.criaExtratoDTO(transacao))
				.collect(Collectors.toList());
	}
	
	public TransacaoDTO criaExtratoDTO(Transacao transacao) {
		return new TransacaoDTO(transacao.getDataTransacao(),transacao.getDescricao(), transacao.getValor());
	}
}
