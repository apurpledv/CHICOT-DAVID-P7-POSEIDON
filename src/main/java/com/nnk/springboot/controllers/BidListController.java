package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

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
public class BidListController {
    @Autowired
    BidListService bidService;

    @RequestMapping({"/bidList/list"})
    public String home(Model model) {
        try {
            model.addAttribute("bidLists", bidService.getAllBidLists());
            log.info("[GET]'/bidList/list' -> bidList/list");
            return "bidList/list";
        } catch (Exception e) {
            log.info("[GET]'/bidList/list' -> home");
            return "home";
        }
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        log.info("[GET]'/bidList/add' -> bidList/add");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Validated BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("[POST]'/bidList/validate' -> bidList/add");
            return "bidList/add";
        }

        try {
            bidService.saveBidList(bid);
            log.info("[POST]'/bidList/validate' => bidList/list");
            return "redirect:/bidList/list";
        } catch (Exception e) {
            log.info("[POST]'/bidList/validate' -> bidList/add");
            return "redirect:/bidList/list";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("bidList", bidService.getBidListById(id));
            log.info("[GET]'/bidList/update/' -> bidList/update");
            return "bidList/update";
        } catch (Exception e) {
            log.info("[GET]'/bidList/update/' => bidList/list");
            return "redirect:/bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Validated BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("[POST]'/bidList/update/' -> bidList/update");
            return "redirect:/bidList/update/" + id.toString();   
        }

        try {
            bidService.saveBidList(bid);
            log.info("[POST]'/bidList/update/' => bidList/list");
            return "redirect:/bidList/list";
        } catch (Exception e) {
            log.info("[POST]'/bidList/update/' => bidList/list");
            return "redirect:/bidList/update/" + id.toString();
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            bidService.deleteBidList(id);
            log.info("[GET]'/bidList/delete/' => bidList/list");
            return "redirect:/bidList/list";
        } catch (Exception e) {
            log.info("[GET]'/bidList/delete/' => bidList/list");
            return "redirect:/bidList/list";
        }
    }
}
