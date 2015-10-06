package objects.dataobjects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name="company")
public class CompanyObject {

	@Id
	private int companyID;
	
	private String companySymbol;
	private int strategy;
	
	@OneToMany(mappedBy="companyObj", fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<StockObject> stocks;
	
	@OneToMany(mappedBy="companyObj", fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<MarketCompanyObject> marketcompany;
	
	public CompanyObject() {
		//super();
	}
	
	public CompanyObject(int companyID, String companySymbol){
		this.companyID=companyID;
		this.companySymbol=companySymbol;
	}
	
	public List<StockObject> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockObject> stocks) {
		this.stocks = stocks;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getCompanySymbol() {
		return companySymbol;
	}

	public void setCompanySymbol(String companySymbol) {
		this.companySymbol = companySymbol;
	}

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}
	
}
