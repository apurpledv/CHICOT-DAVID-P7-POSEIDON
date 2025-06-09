package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RatingController {
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        try {
            model.addAttribute("ratings", ratingService.getAllRatings());
            log.info("[GET]'/rating/list' -> rating/list");
            return "rating/list";
        } catch (Exception e) {
            log.info("[GET]'/rating/list' -> home");
            return "home";
        }
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("[GET]'/rating/add' -> rating/add");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Validated Rating rating, BindingResult result, Model model) {
        if (result.hasErrors() || !rating.isValid()) {
            log.info("[POST]'/rating/validate' -> rating/add");
            return "rating/add";
        }

        try {
            ratingService.saveRating(rating);
            log.info("[POST]'/rating/validate' => rating/list");
            return "redirect:/rating/list";
        } catch (Exception e) {
            log.info("[POST]'/curvePoint/validate' => rating/list");
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("rating", ratingService.getRatingById(id));
            log.info("[GET]'/rating/update/' -> rating/update");
            return "rating/update";
        } catch (Exception e) {
            log.info("[GET]'/rating/update/' => rating/list");
            return "redirect:/rating/list";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Validated Rating rating, BindingResult result, Model model) {
        if (result.hasErrors() || !rating.isValid()) {
            log.info("[POST]'/rating/update' => rating/update");
            return "redirect:/rating/update";
        }

        try {
            ratingService.saveRating(rating);
            log.info("[POST]'/rating/update/' => rating/list");
            return "redirect:/rating/list";
        } catch (Exception e) {
            log.info("[POST]'/rating/update/' => rating/list");
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            ratingService.deleteRating(id);
            log.info("[POST]'/rating/delete/' => rating/list");
            return "redirect:/rating/list";
        } catch (Exception e) {
            log.info("[POST]'/rating/delete/' => rating/list");
            return "redirect:/rating/list";
        }
    }
}
