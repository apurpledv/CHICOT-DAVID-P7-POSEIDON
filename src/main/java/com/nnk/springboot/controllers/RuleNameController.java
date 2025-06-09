package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

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
public class RuleNameController {
    @Autowired
    RuleNameService ruleService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        try {
            model.addAttribute("ruleNames", ruleService.getAllRuleNames());
            log.info("[GET]'/ruleName/list' -> ruleName/list");
            return "ruleName/list";
        } catch (Exception e) {
            log.info("[GET]'/ruleName/list' -> home");
            return "home";
        }
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        log.info("[GET]'/ruleName/add' -> ruleName/add");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Validated RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("[POST]'/ruleName/validate' -> ruleName/add");
            return "ruleName/add";
        }

        try {
            ruleService.saveRuleName(ruleName);
            log.info("[POST]'/ruleName/validate' => ruleName/list");
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            log.info("[POST]'/curvePoint/validate' => ruleName/list");
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("ruleName", ruleService.getRuleNameById(id));
            log.info("[GET]'/ruleName/update/' -> ruleName/update");
            return "ruleName/update";
        } catch (Exception e) {
            log.info("[GET]'/ruleName/update/' => ruleName/list");
            return "redirect:/ruleName/list";
        }
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Validated RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("[POST]'/ruleName/update' => ruleName/update");
            return "redirect:/ruleName/update";
        }

        try {
            ruleService.saveRuleName(ruleName);
            log.info("[POST]'/ruleName/update/' => ruleName/list");
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            log.info("[POST]'/ruleName/update/' => ruleName/list");
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try {
            ruleService.deleteRuleName(id);
            log.info("[POST]'/ruleName/delete/' => ruleName/list");
            return "redirect:/ruleName/list";
        } catch (Exception e) {
            log.info("[POST]'/ruleName/delete/' => ruleName/list");
            return "redirect:/ruleName/list";
        }
    }
}
