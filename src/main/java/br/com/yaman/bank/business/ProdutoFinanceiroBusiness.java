package br.com.yaman.bank.business;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.yaman.bank.DTO.ParamDepositarDTO;
import br.com.yaman.bank.DTO.ParamExtratoDTO;
import br.com.yaman.bank.DTO.ParamLoginDTO;
import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.DTO.ParamTransferirDTO;
import br.com.yaman.bank.DTO.ReturnDepositarDTO;
import br.com.yaman.bank.DTO.ReturnLoginDTO;
import br.com.yaman.bank.DTO.ReturnPerfilDTO;
import br.com.yaman.bank.DTO.ReturnSacarDTO;
import br.com.yaman.bank.DTO.ReturnSaldoContaCorrenteDTO;
import br.com.yaman.bank.DTO.ReturnSaldoContaPoupancaDTO;
import br.com.yaman.bank.DTO.ReturnSaldoDTO;
import br.com.yaman.bank.DTO.ReturnTransferirDTO;
import br.com.yaman.bank.DTO.TransacaoDTO;
import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import br.com.yaman.bank.conta.TipoTransacao;
import br.com.yaman.bank.entity.Cliente;
import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.entity.Transacao;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.mapper.ExtratoMapper;
import br.com.yaman.bank.service.ContaService;
import br.com.yaman.bank.service.ProdutoFinanceiroService;
import br.com.yaman.bank.service.TransacaoService;

@Component
public class ProdutoFinanceiroBusiness {
	private static final String MESAGEM_SUCESSO =  "Transação realizada com sucesso, você possui: ";
	
	@Autowired
	private ProdutoFinanceiroService produtoFinanceiroService;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private ExtratoMapper extratoMapper;
	
	@Autowired
	private ContaService contaService;
	
	public ReturnSacarDTO sacar(ParamSacarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		if(parametros.getValorDoSaque() <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}

		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(parametros.getNumeroConta() , parametros.getAgencia(), parametros.getTipoProdutoFinanceiro());
		
		this.descontarValor(produto, parametros.getValorDoSaque());
		produtoFinanceiroService.alterarProdutoFinanceiro(produto);
		this.salvarTransacao(TipoTransacao.SAQUE.getDescricao(), -parametros.getValorDoSaque(), produto);
		return new ReturnSacarDTO(produto.getValor());
	}
	
	private void salvarTransacao(String descricao, Float valor, ProdutoFinanceiro produtoFinanceiro) {
		Transacao transacao = new Transacao(descricao, valor, produtoFinanceiro);
		transacaoService.salvarTransacao(transacao);
	}
	
	
	public ReturnTransferirDTO transferir(ParamTransferirDTO parametros) throws ProdutoFinanceiroException, NotFoundException {		
		if(parametros.getValorDaTransferencia() <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		ProdutoFinanceiro remetenteProdutoFinanceiro = this.buscarProdutoFinanceiro(parametros.getRemetenteNumeroConta(),parametros.getRemetenteAgencia(),parametros.getRemetenteTipoProdutoFinanceiro());
		ProdutoFinanceiro destinatarioProdutoFinanceiro = this.buscarProdutoFinanceiro(parametros.getDestinatarioNumeroConta(),parametros.getDestinatarioAgencia(),parametros.getDestinatarioTipoProdutoFinanceiro());
		
		this.descontarValorComJuros(remetenteProdutoFinanceiro, parametros.getValorDaTransferencia());
		this.acrescentarValor(destinatarioProdutoFinanceiro, parametros.getValorDaTransferencia() );
		produtoFinanceiroService.alterarProdutoFinanceiro(remetenteProdutoFinanceiro);
		produtoFinanceiroService.alterarProdutoFinanceiro(destinatarioProdutoFinanceiro);
		this.salvarTransacao(TipoTransacao.TRANSFERENCIA.getDescricao(), - parametros.getValorDaTransferencia(), remetenteProdutoFinanceiro);
		this.salvarTransacao(TipoTransacao.TRANSFERENCIA.getDescricao(), parametros.getValorDaTransferencia(), destinatarioProdutoFinanceiro);
		return new ReturnTransferirDTO(remetenteProdutoFinanceiro.getValor());
		
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
	
	public ReturnSaldoDTO buscarSaldo(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException   {
		float valorCorrente;
		float valorPoupanca;
		ProdutoFinanceiro produtoPoupanca = buscarAmbosProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA.getCod());
		ProdutoFinanceiro produtoCorrente = buscarAmbosProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE.getCod());
		if(produtoCorrente == null && produtoPoupanca == null) {
			throw new NotFoundException("Não foi localizado produto financeiro para a agencia: [" + agencia + "] e conta: [" + numeroConta + "] ");
		}
		if(produtoCorrente == null) {
			valorCorrente = 0;
		}else {
			valorCorrente = produtoCorrente.getValor();
		}
		if(produtoPoupanca == null) {
			valorPoupanca = 0;
		}else {
			valorPoupanca = produtoPoupanca.getValor();
		}
		
		return new ReturnSaldoDTO(valorCorrente, valorPoupanca); 
	}
	
	private ProdutoFinanceiro buscarAmbosProdutoFinanceiro(Integer numeroConta, Integer agencia , Integer tipoProdutoFinanceiro) throws NotFoundException, ProdutoFinanceiroException {
		
		if(numeroConta == null || agencia == null || tipoProdutoFinanceiro == null) {
			throw new NotFoundException("Informações de conta ou de Tipo Produto Financeiro inválidos.");
		}
		
		if (tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
			
			ProdutoFinanceiro produto = produtoFinanceiroService.buscarProdutoFinanceiroPeloIdsETipoProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
	
			
			return produto;
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
		
		
	}
	
	public ReturnSaldoContaPoupancaDTO buscarPoupanca(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException   {
		ProdutoFinanceiro produto = buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA.getCod());
		return new ReturnSaldoContaPoupancaDTO(produto.getValor());
	}
	
	public ReturnSaldoContaCorrenteDTO buscarCorrente(Integer numeroConta, Integer agencia) throws NotFoundException, ProdutoFinanceiroException  {
		ProdutoFinanceiro produto = buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE.getCod());
		return new ReturnSaldoContaCorrenteDTO(produto.getValor());
	}

	public ReturnDepositarDTO depositar(ParamDepositarDTO parametros) throws ProdutoFinanceiroException, NotFoundException {
		
		//Validações em primeiro lugar, assim evita processamento desnecessario
		if(parametros.getValorDoDeposito() <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}

		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(parametros.getNumeroConta(), parametros.getAgencia(), parametros.getTipoProdutoFinanceiro());
		
		produto.setValor(produto.getValor() + parametros.getValorDoDeposito());
		produtoFinanceiroService.alterarProdutoFinanceiro(produto);
		this.salvarTransacao(TipoTransacao.DEPOSITO.getDescricao(), parametros.getValorDoDeposito(), produto);
		return  new ReturnDepositarDTO(produto.getValor());
	}
	
	private ProdutoFinanceiro buscarProdutoFinanceiro(Integer numeroConta, Integer agencia , Integer tipoProdutoFinanceiro) throws NotFoundException, ProdutoFinanceiroException {
		
		if(numeroConta == null || agencia == null || tipoProdutoFinanceiro == null) {
			throw new NotFoundException("Informações de conta ou de Tipo Produto Financeiro inválidos.");
		}
		
		if (tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				tipoProdutoFinanceiro == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
			
			ProdutoFinanceiro produto = produtoFinanceiroService.buscarProdutoFinanceiroPeloIdsETipoProdutoFinanceiro(agencia, numeroConta, tipoProdutoFinanceiro);
			
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
		

		List<Transacao> lista = transacaoService.buscaExtrato(dataInicio, dataFim, produtoFinanceiro.getProdutoFinanceiroId());
		System.out.println(new Date().toString());
		return lista;
	}

	public List<TransacaoDTO> exibirExtrato(ParamExtratoDTO parametros) throws Exception {
		//em uma avaliação rapida talvez pudesse retornar apenas o id do produto financeiro
		//pois o produto financeiro é carregado e no final só o id dele é usado
		ProdutoFinanceiro produto = this.buscarProdutoFinanceiro(parametros.getNumeroConta(), parametros.getAgencia(), parametros.getTipoProdutoFinanceiro());
		List<Transacao> lista = this.buscarExtrato(produto, parametros.getDataInicio(), parametros.getDataFim());
		
		return extratoMapper.mapearComStream(lista);
	}

	public ReturnLoginDTO logar(ParamLoginDTO parametros) throws Exception{
		Optional<Conta> contaOptional = contaService.buscarConta(new ContaPK(parametros.getNumeroConta(), parametros.getAgencia()));
		if(contaOptional.isPresent())
		{
			Conta conta = contaOptional.orElse(new Conta());
			if(parametros.getSenha().equals(conta.getSenha())) {
				return new ReturnLoginDTO(true);
			}
		}
		
		throw new NotFoundException("Conta Invalido");
	}

	public ReturnPerfilDTO buscarPerfil(Integer numeroConta, Integer agencia ) throws NotFoundException {
		Optional<Conta> contaOptional = contaService.buscarConta(new ContaPK(numeroConta, agencia));
		if(contaOptional.isPresent())
		{
			Conta conta = contaOptional.orElse(new Conta());
			Cliente cliente = conta.getCliente();
			ReturnPerfilDTO clienteDto = new ReturnPerfilDTO(cliente.getClienteId(), cliente.getNome(), cliente.getCpf(), cliente.getEmail(), "(00) 0000-0000" ,cliente.getEndereco());
			return clienteDto;
		}
		throw new NotFoundException("Perfil Não Encontrado");
		
	}
	
}
