package com.nnk.springboot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    BidListController bidController;

    @MockitoBean 
    BidListService bidService;

    private BidList bid;

    @BeforeEach
    public void setup() {
        bid = new BidList("Account Test", "Type Test", 10d);
    }

    @Test
    @WithMockUser(username="admin", password="$2a$10$Mp3y7EN9m6VbliULkZxR/.q1u96ZOnzFbo6ASTeYZakJ7hZInP9AG", roles={"USER", "ADMIN"})
    public void bidListControllerTest() throws Exception {
        // List View
        this.mockMvc.perform(get("/bidList/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/bidList/add"))
            .andExpect(status().isOk());

        // Add Action
        when(bidService.saveBidList(any(BidList.class))).thenReturn(true);
        this.mockMvc.perform(post("/bidList/validate")
            .with(csrf())
            .flashAttr("bidList", bid))
            .andExpect(status().isFound());

        // Update View
        when(bidService.getBidListById(anyInt())).thenReturn(bid);
        this.mockMvc.perform(get("/bidList/update/2"))
            .andExpect(status().isOk());

        // Update Action
        bid.setAccount("Account Null");
        this.mockMvc.perform(post("/bidList/update/3")
            .with(csrf())
            .flashAttr("bidList", bid))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/bidList/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username="admin", password="$2a$10$Mp3y7EN9m6VbliULkZxR/.q1u96ZOnzFbo6ASTeYZakJ7hZInP9AG", roles={"USER", "ADMIN"})
    public void bidListControllerNonValidTest() throws Exception {
        // List View Non Valid
        when(bidService.getAllBidLists()).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/bidList/list"))
            .andExpect(status().isOk());

        // Add Action Non Valid
        this.mockMvc.perform(post("/bidList/validate")
            .with(csrf()))
            .andExpect(status().isOk());

        this.mockMvc.perform(post("/bidList/validate")
            .with(csrf())
            .flashAttr("bidList", new BidList()))
            .andExpect(status().isOk());

        when(bidService.saveBidList(any(BidList.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(post("/bidList/validate")
            .with(csrf())
            .flashAttr("bidList", bid))
            .andExpect(status().isFound());

        // Update View Non Valid
        when(bidService.getBidListById(any(int.class))).thenAnswer(invocation -> { 
			throw new IllegalArgumentException(); 
		});
        this.mockMvc.perform(get("/bidList/update/-1"))
            .andExpect(status().isFound());

        // Update Action Non Valid
        this.mockMvc.perform(post("/bidList/update/3")
            .with(csrf()))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/bidList/update/3")
            .with(csrf())
            .flashAttr("bidList", bid))
            .andExpect(status().isFound());

        // Delete Action Non Valid
        this.mockMvc.perform(get("/bidList/delete/3"))
            .andExpect(status().isFound());

        when(bidService.deleteBidList(anyInt())).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/bidList/delete/3"))
            .andExpect(status().isFound());
    }
}
