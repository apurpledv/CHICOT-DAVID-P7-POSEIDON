package com.nnk.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.UserController;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Test
    public void homeControllerTest() throws Exception {
        // User Home View
        this.mockMvc.perform(get("/"))
            .andExpect(status().isOk());

        // Admin Home View
        this.mockMvc.perform(get("/admin/home"))
            .andExpect(status().isFound());
    }
}
