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

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Autowired
    RatingController ratingController;

    @MockitoBean 
    RatingService ratingService;

    private Rating rating;

    @BeforeEach
    public void setup() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
    }

    @Test
    @WithMockUser(username="admin", password="$2a$10$Mp3y7EN9m6VbliULkZxR/.q1u96ZOnzFbo6ASTeYZakJ7hZInP9AG", roles={"USER", "ADMIN"})
    public void ratingControllerTest() throws Exception {
        // List View
        this.mockMvc.perform(get("/rating/list"))
            .andExpect(status().isOk());

        // Add View
        this.mockMvc.perform(get("/rating/add"))
            .andExpect(status().isOk());

        // Add Action
        when(ratingService.saveRating(any(Rating.class))).thenReturn(true);
        this.mockMvc.perform(post("/rating/validate")
            .with(csrf())
            .flashAttr("rating", rating))
            .andExpect(status().isFound());

        // Update View
        when(ratingService.getRatingById(anyInt())).thenReturn(rating);
        this.mockMvc.perform(get("/rating/update/2"))
            .andExpect(status().isOk());

        // Update Action
        rating.setMoodysRating("New Moodys Rating");
        this.mockMvc.perform(post("/rating/update/3")
            .with(csrf())
            .flashAttr("rating", rating))
            .andExpect(status().isFound());

        // Delete Action
        this.mockMvc.perform(get("/rating/delete/3"))
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username="admin", password="$2a$10$Mp3y7EN9m6VbliULkZxR/.q1u96ZOnzFbo6ASTeYZakJ7hZInP9AG", roles={"USER", "ADMIN"})
    public void ratingControllerNonValidTest() throws Exception {
        // List View Non Valid
        when(ratingService.getAllRatings()).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/rating/list"))
            .andExpect(status().isOk());

        // Add Action Non Valid
        this.mockMvc.perform(post("/rating/validate")
            .with(csrf()))
            .andExpect(status().isOk());
        
        this.mockMvc.perform(post("/rating/validate")
            .with(csrf())
            .flashAttr("rating", new Rating()))
            .andExpect(status().isOk());

        when(ratingService.saveRating(any(Rating.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(post("/rating/validate")
            .with(csrf())
            .flashAttr("rating", rating))
            .andExpect(status().isFound());

        // Update View Non Valid
        when(ratingService.getRatingById(any(int.class))).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/rating/update/-1"))
            .andExpect(status().isFound());

        // Update Action Non Valid
        this.mockMvc.perform(post("/rating/update/3")
            .with(csrf()))
            .andExpect(status().isFound());

        this.mockMvc.perform(post("/rating/update/3")
            .with(csrf())
            .flashAttr("rating", rating))
            .andExpect(status().isFound());

        // Delete Action Non Valid
        this.mockMvc.perform(get("/rating/delete/3"))
            .andExpect(status().isFound());

        when(ratingService.deleteRating(anyInt())).thenAnswer(invocation -> { 
			throw new Exception(); 
		});
        this.mockMvc.perform(get("/rating/delete/3"))
            .andExpect(status().isFound());
    }
}
