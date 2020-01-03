package br.com.yaman.bank.repositoryvo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import br.com.yaman.bank.repositoryvo.queries.TransacaoQuery;
import br.com.yaman.bank.util.ParseObject;
import br.com.yaman.bank.vo.TransacaoVO;

@Component
public class TransacaoRepositoryVOImpl implements TransacaoRepositoryVO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<TransacaoVO> buscaExtrato(LocalDate dataInicio, LocalDate dataFim, Integer produtoFinanceiroId) throws Exception {
		try {
			boolean temDataInicio = dataInicio != null;
			boolean temDataFim = dataFim != null;
			boolean temProdutoFinanceiro = produtoFinanceiroId != null;
			
			Query query = this.entityManager.createNativeQuery(TransacaoQuery.construirQuery(temDataInicio, temDataFim, temProdutoFinanceiro));
			
			if(temDataInicio && temDataFim && temProdutoFinanceiro) {
				query.setParameter("dataInicio", dataInicio);
				query.setParameter("dataFim", dataFim);
				query.setParameter("produtoFinanceiro", produtoFinanceiroId);
			}
			
			List<Object[]> resultado = query.getResultList();
			
			return resultado.stream()
					.map(transacao -> new TransacaoVO(
							ParseObject.toInteger(transacao[0]),
							ParseObject.toDate(transacao[1]),
							ParseObject.toString(transacao[2]),
							ParseObject.toFloat(transacao[3]),
							ParseObject.toInteger(transacao[4])
							)).collect(Collectors.toList());
					
		}catch(Exception e) {
			throw new Exception("Não foi possivel acessar o extrato", e);
		}
	}
	
	
	
}
