package feed.test;

import static org.junit.Assert.*;
import objects.dataobjects.StockObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import yahooFeed.Feed;

public class FeedtTest {
	
	Feed feed;
	@Before
	public void setUp() throws Exception {
		//feed = new Feed();
	}

	@After
	public void tearDown() throws Exception {
		//feed = null;
	}

	@Test
	public void test() {
		String result;
		StockObject stock;
		stock = Feed.feedConnection("AAPL");
		result = stock.getCompanyObject().getCompanySymbol();
		assertEquals("AAPL", result);
	}

}
