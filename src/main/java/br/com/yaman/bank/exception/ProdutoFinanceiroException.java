package br.com.yaman.bank.exception;

public class ProdutoFinanceiroException extends Exception{
	
	public ProdutoFinanceiroException(String msg) {
		super(msg);
	}
	
	public ProdutoFinanceiroException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
