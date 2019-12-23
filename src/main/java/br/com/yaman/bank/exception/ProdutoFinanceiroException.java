package br.com.yaman.bank.exception;

public class ProdutoFinanceiroException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ProdutoFinanceiroException(String msg) {
		super(msg);
	}
	
	public ProdutoFinanceiroException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
