package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;

@SpringBootTest
public class TradeServiceTest {
    @Autowired
	private TradeService tradeService;

    @MockitoBean
    private TradeRepository tradeRepository;

    @Test
    public void tradeServiceTest() throws Exception {
        Trade trade = new Trade("Trade Account", "Type", 2d);

		// Save
		assertTrue(tradeService.saveTrade(trade));

		// Update
		trade.setAccount("New Trade Account");
		assertTrue(tradeService.saveTrade(trade));

		// Get RuleNames
		assertTrue(tradeService.getAllTrades() instanceof List);

        // Get One RuleName
        when(tradeRepository.getReferenceById(anyInt())).thenReturn(trade);
        assertTrue(tradeService.getTradeById(1) instanceof Trade);
        
		// Delete
		assertTrue(tradeService.deleteTrade(1));
    }
}
