package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
	private UserRepository userRepository;

    @Test
    public void userRepositoryTest() {
        DBUser user = new DBUser();
        user.setUsername("newUser");
        user.setFullname("newUser");
        user.setRole("user");
        user.setPassword("newUser");

		// Save
		user = userRepository.save(user);
		assertNotNull(user.getId());
		assertTrue(user.getUsername().equals("newUser"));

		// Update
		user.setUsername("newUser2");
		user = userRepository.save(user);
		assertTrue(user.getUsername().equals("newUser2"));

		// Find
		List<DBUser> listResult = userRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = user.getId();
		userRepository.delete(user);
		Optional<DBUser> userList = userRepository.findById(id);
		assertFalse(userList.isPresent());
    }
}
