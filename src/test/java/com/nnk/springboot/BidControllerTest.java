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

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTest {
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
    public void userControllerTest() throws Exception {
        /*// List View
        this.mockMvc.perform(get("/user/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/user/add"))
            .andExpect(status().isOk());

        // Add Action
        this.mockMvc.perform(post("/user/validate")
            .flashAttr("user", user))
            .andExpect(status().isFound());

        // Update View
        when(userService.getUser(anyInt())).thenReturn(user);
        this.mockMvc.perform(get("/user/update/2"))
            .andExpect(status().isOk());

        // Update Action
        user.setUsername("newUser2");
        user.setPassword("newUser");
        this.mockMvc.perform(post("/user/update/3")
            .flashAttr("user", user))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/user/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    public void userControllerNonValidTest() throws Exception {
        // Add View Non Valid
        this.mockMvc.perform(post("/user/validate"))
            .andExpect(status().isOk());

        // Update View Non Valid
        when(userService.getUser(any(int.class))).thenAnswer(invocation -> { 
			throw new IllegalArgumentException(); 
		});
        this.mockMvc.perform(get("/user/update/-1"))
            .andExpect(status().isOk());

        // Update Action Non Valid
        this.mockMvc.perform(post("/user/update/3"))
            .andExpect(status().isOk());

        // Delete Action Non Valid
        this.mockMvc.perform(get("/user/delete/3"))
            .andExpect(status().isFound());*/
    }
}
