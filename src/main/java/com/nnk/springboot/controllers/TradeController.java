package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

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
public class TradeController {
    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        try {
            model.addAttribute("trades", tradeService.getAllTrades());
            log.info("[GET]'/trade/list' -> trade/list");
            return "trade/list";
        } catch (Exception e) {
            log.info("[GET]'/trade/list' -> home");
            return "home";
        }
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        log.info("[GET]'/trade/add' -> trade/add");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Validated Trade trade, BindingResult result, Model model) {
        if (result.hasErrors() || !trade.isValid()) {
            log.info("[POST]'/trade/validate' -> trade/add");
            return "trade/add";
        }

        try {
            tradeService.saveTrade(trade);
            log.info("[POST]'/trade/validate' => trade/list");
            return "redirect:/trade/list";
        } catch (Exception e) {
            log.error(e.toString());
            log.info("[POST]'/curvePoint/validate' => trade/list");
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("trade", tradeService.getTradeById(id));
            log.info("[GET]'/trade/update/' -> trade/update");
            return "trade/update";
        } catch (Exception e) {
            log.info("[GET]'/trade/update/' => trade/list");
            return "redirect:/trade/list";
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Validated Trade trade, BindingResult result, Model model) {
        if (result.hasErrors() || !trade.isValid()) {
            log.info("[POST]'/trade/update' => trade/update");
            return "redirect:/trade/update/" + id.toString();
        }

        try {
            tradeService.saveTrade(trade);
            log.info("[POST]'/trade/update/' => trade/list");
            return "redirect:/trade/list";
        } catch (Exception e) {
            log.info("[POST]'/trade/update/' => trade/list");
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            tradeService.deleteTrade(id);
            log.info("[POST]'/trade/delete/' => trade/list");
            return "redirect:/trade/list";
        } catch (Exception e) {
            log.info("[POST]'/trade/delete/' => trade/list");
            return "redirect:/trade/list";
        }
    }
}
