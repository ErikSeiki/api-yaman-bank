package br.com.yaman.bank.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamExtratoDTO;
import br.com.yaman.bank.DTO.ParamLoginDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.DTO.ParamTransferirDTO;
import br.com.yaman.bank.DTO.TransacaoDTO;
import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import br.com.yaman.bank.conta.TipoTransacao;
import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.entity.Transacao;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.mapper.ExtratoMapper;
import br.com.yaman.bank.repository.ProdutoFinanceiroRepository;
import br.com.yaman.bank.repository.TransacaoRepository;

@Service
public class ProdutoFinanceiroService {

	private static final String MESAGEM_SUCESSO =  "Transação realizada com sucesso, você possui: ";
	
	@Autowired
	private ProdutoFinanceiroRepository produtoFinanceiroRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private ExtratoMapper extratoMapper;
	
	public String sacar(ParamSacarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		float valorDoSaque = parametros.getValorDoSaque();
		
		if(valorDoSaque <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}

		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(numeroConta , agencia, tipoProdutoFinanceiro);
		
		this.descontarValor(produto, valorDoSaque);
		produtoFinanceiroRepository.save(produto);
		this.salvarTransacao(TipoTransacao.SAQUE.getDescricao(), -valorDoSaque, produto);
		return MESAGEM_SUCESSO + produto.getValor();
		
	}
	
	private void salvarTransacao(String descricao, Float valor, ProdutoFinanceiro produtoFinanceiro) {
		Transacao transacao = new Transacao(descricao, valor, produtoFinanceiro);
		transacaoRepository.save(transacao);
	}
	
	
	public String transferir(ParamTransferirDTO parametros) throws ProdutoFinanceiroException, NotFoundException {

		float valorDaTransferencia = parametros.getValorDaTransferencia();
		
		if(valorDaTransferencia <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		ProdutoFinanceiro remetenteProdutoFinanceiro = this.buscarProdutoFinanceiro(parametros.getRemetenteNumeroConta(),parametros.getRemetenteAgencia(),parametros.getRemetenteTipoProdutoFinanceiro());
		ProdutoFinanceiro destinatarioProdutoFinanceiro = this.buscarProdutoFinanceiro(parametros.getDestinatarioNumeroConta(),parametros.getDestinatarioAgencia(),parametros.getDestinatarioTipoProdutoFinanceiro());
		
		this.descontarValorComJuros(remetenteProdutoFinanceiro, valorDaTransferencia);
		this.acrescentarValor(destinatarioProdutoFinanceiro, valorDaTransferencia );
		produtoFinanceiroRepository.save(remetenteProdutoFinanceiro);
		produtoFinanceiroRepository.save(destinatarioProdutoFinanceiro);
		this.salvarTransacao(TipoTransacao.TRANSFERENCIA.getDescricao(), -valorDaTransferencia, remetenteProdutoFinanceiro);
		this.salvarTransacao(TipoTransacao.TRANSFERENCIA.getDescricao(), valorDaTransferencia, destinatarioProdutoFinanceiro);
		return MESAGEM_SUCESSO + remetenteProdutoFinanceiro.getValor();
		
	}
	
	private void descontarValorComJuros(ProdutoFinanceiro produtoFinanceiro, float valorDaTransferencia) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorDaTransferencia) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - (valorDaTransferencia * 1.1f));
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	private void descontarValor(ProdutoFinanceiro produtoFinanceiro, float valorSaque) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorSaque) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - valorSaque);
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	public void acrescentarValor(ProdutoFinanceiro produtoFinanceiro, float valorDaTransferencia){
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() +valorDaTransferencia);
	}
	
	public ProdutoFinanceiro buscarPoupanca(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException   {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA.getCod());
	}
	
	public ProdutoFinanceiro buscarCorrente(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException  {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE.getCod());
	}

	public String depositar(ParamDepositarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		float valorDoDeposito = parametros.getValorDoDeposito();
		
		//Validações em primeiro lugar, assim evita processamento desnecessario
		if(valorDoDeposito <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}

		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(numeroConta, agencia, tipoProdutoFinanceiro);
		
		produto.setValor(produto.getValor() + valorDoDeposito);
		produtoFinanceiroRepository.save(produto);
		this.salvarTransacao(TipoTransacao.DEPOSITO.getDescricao(), valorDoDeposito, produto);
		return MESAGEM_SUCESSO + produto.getValor();
	}
	
	private ProdutoFinanceiro buscarProdutoFinanceiro(Integer numeroConta, Integer agencia , Integer tipoProdutoFinanceiro) throws NotFoundException, ProdutoFinanceiroException {
		
		if(numeroConta == null || agencia == null || tipoProdutoFinanceiro == null) {
			throw new NotFoundException("Informações de conta ou de Tipo Produto Financeiro inválidos.");
		}
		
		if (tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
			
			ProdutoFinanceiro produto = produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
			
			if(produto == null)
				throw new NotFoundException("Não foi localizado produto financeiro para a agencia: [" + agencia + "] e conta: [" + numeroConta + "] ");
			
			return produto;
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
		
		
	}
	
	private List<Transacao> buscarExtrato(ProdutoFinanceiro produtoFinanceiro, LocalDate dataInicio, LocalDate dataFim) throws Exception {
		if(produtoFinanceiro == null || dataInicio == null || dataFim == null) {
			throw new NotFoundException("Informações Produto Financeiro ou Datas inválidos.");
		}
		

		List<Transacao> lista = transacaoRepository.buscaExtrato(dataInicio, dataFim, produtoFinanceiro.getProdutoFinanceiroId());
		System.out.println(new Date().toString());
		return lista;
	}

	public List<TransacaoDTO> exibirExtrato(ParamExtratoDTO parametros) throws Exception {
		Integer numeroConta = parametros.getNumeroConta();
		Integer agencia = parametros.getAgencia();
		Integer tipoProdutoFinanceiro = parametros.getTipoProdutoFinanceiro();
		LocalDate dataInicio = parametros.getDataInicio();
		LocalDate dataFim = parametros.getDataFim();
		
		//em uma avaliação rapida talvez pudesse retornar apenas o id do produto financeiro
		//pois o produto financeiro é carregado e no final só o id dele é usado
		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(numeroConta, agencia, tipoProdutoFinanceiro);
		List<Transacao> lista = this.buscarExtrato(produto, dataInicio, dataFim);
		
		return extratoMapper.mapearComStream(lista);
	}

	public String logar(ParamLoginDTO parametros) throws Exception{
		if(parametros.getAgencia() != 1234 || parametros.getNumeroConta() != 123456 || parametros.getSenha() != 123) {
			throw new NotFoundException("Conta não exixtente");
		}
		return "Logado";
	}
	
}
