package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepo;

    public List<Rating> getAllRatings() {
        return ratingRepo.findAll();
    }

    public Rating getRatingById(int id) {
        return ratingRepo.getReferenceById(id);
    }

    public boolean saveRating(Rating rating) {
        ratingRepo.save(rating);
        return true;
    }

    public boolean deleteRating(int id) {
        ratingRepo.delete(getRatingById(id));
        return true;
    }
}
