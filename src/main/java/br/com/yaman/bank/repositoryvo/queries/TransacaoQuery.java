package br.com.yaman.bank.repositoryvo.queries;

public class TransacaoQuery {
	
	private TransacaoQuery(){}
	
	public static String construirQuery(boolean temDataInicio, boolean temDataFim, boolean temProdutoFinanceiro) {
		StringBuilder query = new StringBuilder();
		
		query.append("select * from Transacao t ");
		if(temDataInicio && temDataFim && temProdutoFinanceiro) {
			query.append("where t.`DATA_TRANSACAO` >= :dataInicio  ")
			.append("and t.`DATA_TRANSACAO` <= :dataFim ")
			.append("and t.`FK_PRODUTO_FINANCEIRO` = :produtoFinanceiro ;");
		}
		return query.toString();
	}
}
