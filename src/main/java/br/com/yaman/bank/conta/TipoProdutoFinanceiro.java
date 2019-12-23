package br.com.yaman.bank.conta;

public enum TipoProdutoFinanceiro {

	CONTA_POUPANCA(1 , "Conta Poupança"),
	CONTA_CORRENTE(2 , "Conta Corrente");
	
	int cod;
	String descricao;
	
	private TipoProdutoFinanceiro(int cod,String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
