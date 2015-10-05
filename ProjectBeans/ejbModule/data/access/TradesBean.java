package data.access;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import objects.dataobjects.CompanyObject;
import objects.dataobjects.StockObject;
import objects.dataobjects.TradeHistoryObject;

//import object.dataobjects.*;

@Stateless
@Local(TradesBeanLocal.class)
@Remote(TradesBeanRemote.class)
public class TradesBean implements TradesBeanLocal, TradesBeanRemote {

	@PersistenceContext(unitName = "JPADB")
	private EntityManager entityManager;
	public TradesBean() {
	}
	
	@Override
	public void addStock(StockObject stock) {
		entityManager.persist(stock);
	}
	
	@Override
	public Boolean companyCheck(CompanyObject comp) {
		System.out.println(entityManager.contains(comp));
		return entityManager.contains(comp);
	}
	
	@Override
	public CompanyObject getCompany(String symbol) {
		
		CompanyObject comp = null;
		Query query = entityManager.createQuery(
			    "SELECT c FROM "+CompanyObject.class.getName()+" c WHERE c.companySymbol = :compName");
			query.setParameter("compName", symbol);
		try {
			comp = (CompanyObject) query.getSingleResult();
		} catch(NoResultException ex) {
			comp =null;
		}

		//CompanyObject comp = entityManager.find(CompanyObject.class, symbol);
		return comp;
	}
	
	@Override
	public void addCompany(CompanyObject company) {
		entityManager.persist(company);
	}
	
	@Override
	public void addTrade(TradeHistoryObject trade) {
		entityManager.persist(trade);
	}
	
	@Override
	public List<TradeHistoryObject> getAllTrades() {
		String q = "SELECT s FROM "+TradeHistoryObject.class.getName()+" s";
		Query query = entityManager.createQuery(q);
		List<TradeHistoryObject> trades = query.getResultList();
		return trades;
	}
	
	
}
