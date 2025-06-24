package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

@SpringBootTest
public class UserServiceTest {
    @Autowired
	private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void userServiceTest() throws Exception {
        DBUser user = new DBUser();
        user.setUsername("newUser");
        user.setFullname("newUser");
        user.setRole("user");
        user.setPassword("newUser");

		// Save
		assertTrue(userService.saveUser(user));

		// Update
		user.setUsername("newUser2");
		assertTrue(userService.saveUser(user));

		// Get Users
		assertTrue(userService.getUsers() instanceof List);

        // Get One User
        when(userRepository.getReferenceById(anyInt())).thenReturn(user);
        assertTrue(userService.getUser(1) instanceof DBUser);
        
		// Delete
		assertTrue(userService.deleteUser(user));
    }
}
