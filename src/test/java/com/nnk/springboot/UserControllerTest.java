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

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @MockitoBean 
    UserService userService;

    private DBUser user;

    @BeforeEach
    public void setup() {
        user = new DBUser();
        user.setUsername("newUser");
        user.setFullname("newUser");
        user.setRole("USER");
        user.setPassword("newUser8$");
    }

    @Test
    @WithMockUser(username="admin", password="$2a$10$Mp3y7EN9m6VbliULkZxR/.q1u96ZOnzFbo6ASTeYZakJ7hZInP9AG", roles={"USER", "ADMIN"})
    public void userControllerTest() throws Exception {
        // List View
        this.mockMvc.perform(get("/user/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/user/add"))
            .andExpect(status().isOk());

        // Add Action
        this.mockMvc.perform(post("/user/validate")
            .with(csrf())
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
            .with(csrf())
            .flashAttr("user", user))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/user/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username="admin", password="$2a$10$Mp3y7EN9m6VbliULkZxR/.q1u96ZOnzFbo6ASTeYZakJ7hZInP9AG", roles={"USER", "ADMIN"})
    public void userControllerNonValidTest() throws Exception {
        // Add Action Non Valid
        when(userService.saveUser(any(DBUser.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(post("/user/validate")
            .with(csrf()))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/user/validate")
            .with(csrf())
            .flashAttr("user", user))
            .andExpect(status().isFound());

        // Update View Non Valid
        when(userService.getUser(any(int.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/user/update/-1"))
            .andExpect(status().isOk());

        // Update Action Non Valid
        this.mockMvc.perform(post("/user/update/3")
            .with(csrf()))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/user/update/3")
            .with(csrf())
            .flashAttr("user", user))
            .andExpect(status().isFound());

        user.setPassword("");
        this.mockMvc.perform(post("/user/update/3")
            .with(csrf())
            .flashAttr("user", user))
            .andExpect(status().isFound());
            
        user.setPassword("newUser8$");

        // Delete Action Non Valid
        this.mockMvc.perform(get("/user/delete/3"))
            .andExpect(status().isFound());
    }
}
