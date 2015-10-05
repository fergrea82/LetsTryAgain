package project.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.dataobjects.StockObject;
import objects.dataobjects.TradeHistoryObject;

import org.jboss.logging.Logger;

import data.access.TradesBeanLocal;

/**
 * Servlet implementation class TradingHistoryServlet
 */
@WebServlet("/TradingHistoryServlet")
@EJB(name="ejb/TradesBean",beanInterface=TradesBeanLocal.class)
public class TradingHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(CompanySymbolServlet.class);
    private List<TradeHistoryObject> trades = new ArrayList<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TradingHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InitialContext context;
		try {
			context = new InitialContext();
		
			TradesBeanLocal bean = (TradesBeanLocal)context.lookup("java:comp/env/ejb/TradesBean");
			
			trades = bean.getAllTrades();
			
		} catch(NamingException e) {
			log.error("NamingException: " + e.getMessage());
			e.printStackTrace();
		}
		
		request.setAttribute("trades", trades);
		request.getRequestDispatcher("/TradingHistory.jsp").include(request, response);
	}

}
