package br.com.yaman.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yaman.bank.DTO.ParamSacarDTO;
import br.com.yaman.bank.DTO.ParamTransferirDTO;
import br.com.yaman.bank.conta.TipoProdutoFinanceiro;
import br.com.yaman.bank.entity.Conta;
import br.com.yaman.bank.entity.ContaPK;
import br.com.yaman.bank.entity.ProdutoFinanceiro;
import br.com.yaman.bank.exception.NotFoundException;
import br.com.yaman.bank.exception.ProdutoFinanceiroException;
import br.com.yaman.bank.repository.ContaRepository;
import br.com.yaman.bank.repository.ProdutoFinanceiroRepository;

@Service
public class ProdutoFinanceiroService {
	
	private static final String MESAGEM_SUCESSO_SAQUE =  "Saque sucedido, você possui: ";
	private static final String MENSAGEM_SUCESSO_TRANSFERENCIA = "Transferencia sucedida, você possui: ";
	
	@Autowired
	private ProdutoFinanceiroRepository produtoFinanceiroRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	public String sacar(ParamSacarDTO parametro) throws ProdutoFinanceiroException {
		
		ProdutoFinanceiro produtoFinanceiro = produtoFinanceiroRepository.findById(parametro.getProdutoFinanceiroId()).orElse(null);
		
		float valorDoSaque = parametro.getValorDoSaque();
		
		if(produtoFinanceiro == null) {
			throw new ProdutoFinanceiroException("Produto financeiro invalido");
		}
		
		if(valorDoSaque <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		if(produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				produtoFinanceiro.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()	) {
		
			this.descontarValor(produtoFinanceiro, valorDoSaque);
			produtoFinanceiroRepository.save(produtoFinanceiro);
			return MESAGEM_SUCESSO_SAQUE + produtoFinanceiro.getValor();
		
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
	}
	
	public String transferir(ParamTransferirDTO parametros) throws ProdutoFinanceiroException {
		ProdutoFinanceiro produtoFinanceiroMinhaConta = produtoFinanceiroRepository.findById(parametros.getProdutoFinanceiroIdMinhaConta()).orElse(null);
		ProdutoFinanceiro produtoFinanceiroOutraConta =  produtoFinanceiroRepository.buscarProdutoFinanceiro(parametros.getAgenciaOutraConta(), parametros.getNumeroOutraConta(), parametros.getTipoProdutoFinanceiro());
		
		float valorDaTransferencia = parametros.getValorDaTransferencia();
		
		if(produtoFinanceiroMinhaConta == null) {
			throw new ProdutoFinanceiroException("Remetente do produto financeiro invalido");
		}
		
		if(produtoFinanceiroOutraConta == null) {
			throw new ProdutoFinanceiroException("Destinatario do produto financeiro invalido");
		}
		
		if(valorDaTransferencia <= 0) {
			throw new ProdutoFinanceiroException("Valor invalido");
		}
		
		if(produtoFinanceiroMinhaConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
				produtoFinanceiroMinhaConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod() && 
				produtoFinanceiroOutraConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_POUPANCA.getCod() || 
						produtoFinanceiroOutraConta.getTipoProdutoFinanceiro().getTipoProdutoFinanceiroId() == TipoProdutoFinanceiro.CONTA_CORRENTE.getCod()) {
		
			this.descontarValorComJuros(produtoFinanceiroMinhaConta, valorDaTransferencia);
			this.acrescentarValor(produtoFinanceiroOutraConta, valorDaTransferencia );
			produtoFinanceiroRepository.save(produtoFinanceiroMinhaConta);
			produtoFinanceiroRepository.save(produtoFinanceiroOutraConta);
			return MENSAGEM_SUCESSO_TRANSFERENCIA + produtoFinanceiroMinhaConta.getValor();
		
		}
		
		throw new ProdutoFinanceiroException("Tipo produto financeiro não configurado");
	}
	
	public void descontarValorComJuros(ProdutoFinanceiro produtoFinanceiro, float valorDaTransferencia) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorDaTransferencia) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - (valorDaTransferencia * 1.1f));
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	public void descontarValor(ProdutoFinanceiro produtoFinanceiro, float valorSaque) throws ProdutoFinanceiroException {
		
		if(produtoFinanceiro.getValor() >= valorSaque) {
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() - valorSaque);
		}else {
			throw new ProdutoFinanceiroException("Valor superior ao saldo, você possui: " +  produtoFinanceiro.getValor());
		}
	}
	
	public void acrescentarValor(ProdutoFinanceiro produtoFinanceiro, float valorDaTransferencia){
			produtoFinanceiro.setValor(produtoFinanceiro.getValor() +valorDaTransferencia);
	}
	
	public ProdutoFinanceiro buscarPoupanca(Integer numeroConta, Integer agencia) throws Exception  {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_POUPANCA);
	}
	
	public ProdutoFinanceiro buscarCorrente(Integer numeroConta, Integer agencia) throws Exception {
		return buscarProdutoFinanceiro(numeroConta, agencia,TipoProdutoFinanceiro.CONTA_CORRENTE);
	}

	private ProdutoFinanceiro buscarProdutoFinanceiro(Integer numeroConta, Integer agencia , TipoProdutoFinanceiro tipoProdutoFinanceiro) throws NotFoundException {
		ProdutoFinanceiro produto = produtoFinanceiroRepository.buscarProdutoFinanceiro(agencia, numeroConta,tipoProdutoFinanceiro.getCod());
		if(produto==null)
			throw new NotFoundException("Essa conta não possui um(a) " + tipoProdutoFinanceiro.getDescricao());

		return produto;
	}


	
}
