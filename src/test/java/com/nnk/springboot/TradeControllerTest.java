package com.nnk.springboot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    TradeController tradeController;

    @MockitoBean 
    TradeService tradeService;

    private Trade trade;

    @BeforeEach
    public void setup() {
        trade = new Trade("Trade Account", "Type");
    }

    @Test
    public void tradeControllerTest() throws Exception {
        // List View
        this.mockMvc.perform(get("/trade/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/trade/add"))
            .andExpect(status().isOk());

        // Add Action
        when(tradeService.saveTrade(any(Trade.class))).thenReturn(true);
        this.mockMvc.perform(post("/trade/validate")
            .flashAttr("trade", trade))
            .andExpect(status().isFound());

        // Update View
        when(tradeService.getTradeById(anyInt())).thenReturn(trade);
        this.mockMvc.perform(get("/trade/update/2"))
            .andExpect(status().isOk());

        // Update Action
        trade.setAccount("New Trade Account");
        this.mockMvc.perform(post("/trade/update/3")
            .flashAttr("trade", trade))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/trade/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    public void tradeControllerNonValidTest() throws Exception {
        // List View Non Valid
        when(tradeService.getAllTrades()).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/trade/list"))
            .andExpect(status().isOk());

        // Add Action Non Valid
        this.mockMvc.perform(post("/trade/validate"))
            .andExpect(status().isOk());

        when(tradeService.saveTrade(any(Trade.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(post("/trade/validate")
            .flashAttr("trade", trade))
            .andExpect(status().isFound());

        // Update View Non Valid
        when(tradeService.getTradeById(any(int.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/trade/update/-1"))
            .andExpect(status().isFound());

        // Update Action Non Valid
        this.mockMvc.perform(post("/trade/update/3"))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/trade/update/3")
            .flashAttr("trade", trade))
            .andExpect(status().isFound());

        // Delete Action Non Valid
        this.mockMvc.perform(get("/trade/delete/3"))
            .andExpect(status().isFound());

        when(tradeService.deleteTrade(anyInt())).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/trade/delete/3"))
            .andExpect(status().isFound());
    }
}
