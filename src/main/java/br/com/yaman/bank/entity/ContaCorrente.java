package br.com.yaman.bank.entity;


public class ContaCorrente extends ProdutoFinanceiro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sacar(float valor) {
		this.valor -= valor;
	}

	@Override
	public void depositar(float valor) {
		this.valor += valor;
		
	}

	@Override
	public void transferir(float valor, ProdutoFinanceiro contaDestino) {
		// TODO Auto-generated method stub
		
	}

}
