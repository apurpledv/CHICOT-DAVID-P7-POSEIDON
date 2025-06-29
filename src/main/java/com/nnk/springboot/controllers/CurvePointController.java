package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

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
public class CurvePointController {
    @Autowired
    CurvePointService curveService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        try {
            model.addAttribute("curvePoints", curveService.getAllCurvePoints());
            log.info("[GET]'/curvePoint/list' -> curvePoint/list");
            return "curvePoint/list";
        } catch (Exception e) {
            log.info("[GET]'/curvePoint/list' -> home");
            return "home";
        }
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        log.info("[GET]'/curvePoint/add' -> curvePoint/add");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Validated CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors() || !curvePoint.isValid()) {
            log.info("[POST]'/curvePoint/validate' -> curvePoint/add");
            return "curvePoint/add";
        }

        try {
            curveService.saveCurvePoint(curvePoint);
            log.info("[POST]'/curvePoint/validate' => curvePoint/list");
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            log.info("[POST]'/curvePoint/validate' => curvePoint/list");
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("curvePoint", curveService.getCurvePointById(id));
            log.info("[GET]'/curvePoint/update/' -> curvePoint/update");
            return "curvePoint/update";
        } catch (Exception e) {
            log.info("[GET]'/curvePoint/update/' => curvePoint/list");
            return "redirect:/curvePoint/list";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Validated CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors() || !curvePoint.isValid()) {
            log.info("[POST]'/curvePoint/update' => curvePoint/update");
            return "redirect:/curvePoint/update/" + id.toString();
        }

        try {
            curveService.saveCurvePoint(curvePoint);
            log.info("[POST]'/curvePoint/update/' => curvePoint/list");
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            log.info("[POST]'/curvePoint/update/' => curvePoint/list");
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        try {
            curveService.deleteCurvePoint(id);
            log.info("[POST]'/curvePoint/delete/' => curvePoint/list");
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            log.info("[POST]'/curvePoint/delete/' => curvePoint/list");
            return "redirect:/curvePoint/list";
        }
    }
}
