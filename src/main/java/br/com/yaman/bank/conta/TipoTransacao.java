package br.com.yaman.bank.conta;

public enum TipoTransacao {
	
	SAQUE("Saque"),
	TRANSFERENCIA("Transferecia"),
	DEPOSITO("Depósito");
	
	
	
	int cod;
	String descricao;
	
	private TipoTransacao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
}
