package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;

@SpringBootTest
public class RatingServiceTest {
    @Autowired
	private RatingService ratingService;

    @MockitoBean
    private RatingRepository ratingRepository;

    @Test
    public void ratingServiceTest() throws Exception {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		assertTrue(ratingService.saveRating(rating));

		// Update
		rating.setMoodysRating("New Moodys Rating");
		assertTrue(ratingService.saveRating(rating));

		// Get Curve Ratings
		assertTrue(ratingService.getAllRatings() instanceof List);

        // Get One Rating
        when(ratingRepository.getReferenceById(anyInt())).thenReturn(rating);
        assertTrue(ratingService.getRatingById(1) instanceof Rating);
        
		// Delete
		assertTrue(ratingService.deleteRating(10));
    }
}
