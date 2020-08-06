package com.mac.trader;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mac.trader.entity.TradeEntity;
import com.mac.trader.repository.TradeRepository;

/***
 * Unit Tests
 * @author Mac
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	TradeRepository tradeRepo;

	/***
	 * Test Get Method
	 * @throws Exception
	 */
	@Test
	public void testGetTrades() throws Exception {

		TradeEntity trade = new TradeEntity();
		trade.setTradeId("TC1");
		trade.setVersion(1);
		trade.setCounterPartyId("CP-1");
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now());
		trade.setCreatedDate(LocalDate.now());
		trade.setExpired("N");

		List<TradeEntity> lstTrade = new ArrayList<>();
		lstTrade.add(trade);

		Mockito.when(tradeRepo.findAll()).thenReturn(lstTrade);
		this.mockMvc.perform(get("/trader")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	/***
	 * Test Post Method
	 * @throws Exception
	 */
	@Test
	public void testPostTrade() throws Exception {
		String jsonTrade = "{\"tradeId\": \"TC1\",\"version\": 1,\"counterPartyId\": \"CP-1\",\"bookId\": \"B1\",\"maturityDate\": \"02-02-2020\",\"createdDate\": \"04-08-2020\",\"expired\": \"Y\"}";
		this.mockMvc.perform(post("/trader").contentType(MediaType.APPLICATION_JSON).content(jsonTrade))
				.andExpect(status().isOk());
	}

	/***
	 * Test Trade Creation for Trade having higher version
	 * @throws Exception
	 */
	@Test
	public void testPostTradeHigherVersion() throws Exception {
		String jsonTrade = "{\"tradeId\": \"TC1\",\"version\": 1,\"counterPartyId\": \"CP-1\",\"bookId\": \"B1\",\"maturityDate\": \"02-02-2020\",\"createdDate\": \"04-08-2020\",\"expired\": \"Y\"}";
		this.mockMvc.perform(post("/trader").contentType(MediaType.APPLICATION_JSON).content(jsonTrade))
				.andExpect(status().isOk());

		jsonTrade = "{\"tradeId\": \"TC1\",\"version\": 2,\"counterPartyId\": \"CP-1\",\"bookId\": \"B1\",\"maturityDate\": \"02-02-2020\",\"createdDate\": \"04-08-2020\",\"expired\": \"Y\"}";
		this.mockMvc.perform(post("/trader").contentType(MediaType.APPLICATION_JSON).content(jsonTrade))
				.andExpect(status().isOk());
	}

	/***
	 * Test trade creation for trade having lower version.
	 * @throws Exception
	 */
	@Test
	public void testPostTradeLowerVersion() throws Exception {

		TradeEntity trade = new TradeEntity();
		trade.setTradeId("TC1");
		trade.setVersion(3);
		trade.setCounterPartyId("CP-1");
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now());
		trade.setCreatedDate(LocalDate.now());
		trade.setExpired("N");

		Mockito.when(tradeRepo.findFirstByTradeIdOrderByVersionDesc(ArgumentMatchers.anyString()))
				.thenReturn(Optional.of(trade));

		String jsonTrade = "{\"tradeId\": \"TC1\",\"version\": 1,\"counterPartyId\": \"CP-1\",\"bookId\": \"B1\",\"maturityDate\": \"02-02-2020\",\"createdDate\": \"04-08-2020\",\"expired\": \"Y\"}";
		this.mockMvc.perform(post("/trader").contentType(MediaType.APPLICATION_JSON).content(jsonTrade))
				.andExpect(status().isBadRequest());
	}
	
	/***
	 * Test for trade with same versions.
	 * @throws Exception
	 */
	@Test
	public void testPostTradeEqualVersion() throws Exception {

		TradeEntity trade = new TradeEntity();
		trade.setTradeId("TC1");
		trade.setVersion(3);
		trade.setCounterPartyId("CP-1");
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now());
		trade.setCreatedDate(LocalDate.now());
		trade.setExpired("N");

		Mockito.when(tradeRepo.findFirstByTradeIdOrderByVersionDesc(ArgumentMatchers.anyString()))
				.thenReturn(Optional.of(trade));

		String jsonTrade = "{\"tradeId\": \"TC1\",\"version\": 3,\"counterPartyId\": \"CP-3\",\"bookId\": \"B3\",\"maturityDate\": \"02-02-2020\",\"createdDate\": \"04-08-2020\",\"expired\": \"Y\"}";
		this.mockMvc.perform(post("/trader").contentType(MediaType.APPLICATION_JSON).content(jsonTrade))
				.andExpect(status().isOk());
	}
}
