package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        log.info("[GET]'/user/list' -> user/list");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new DBUser());
        log.info("[GET]'/user/add' -> user/add");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Validated DBUser user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            redirectAttributes.addFlashAttribute("passwordError", "Mot de passe non valide. Veuillez renseigner un mot de passe contenant : 8 caractères, 1 majuscule, 1 symbole et 1 chiffre minimums.");
            log.info("[POST]'/user/validate' -> user/add");
            return "redirect:/user/add";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveUser(user);
            model.addAttribute("users", userService.getUsers());
            log.info("[POST]'/user/validate' => user/list");
            return "redirect:/user/list";
        } catch (Exception e) {
            log.error(e.toString());
            log.info("[POST]'/user/validate' => user/list");
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            DBUser user = userService.getUser(id);
            user.setPassword("");
            model.addAttribute("user", user);
            log.info("[GET]'/user/update/' -> user/list");
            return "user/update";
        } catch (Exception e) {
            log.info("[GET]'/user/update/' -> home");
            return "home";
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Validated DBUser user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("[POST]'/user/update/' -> user/update");
            return "user/update";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
            userService.saveUser(user);
            model.addAttribute("users", userService.getUsers());
            log.info("[POST]'/user/update/' => user/list");
            return "redirect:/user/list";
        } catch (Exception e) {
            log.info("[POST]'/user/update/' => user/list");
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try {
            DBUser user = userService.getUser(id);
            userRepository.delete(user);
            model.addAttribute("users", userRepository.findAll());
            log.info("[POST]'/user/update/' => user/list");
            return "redirect:/user/list";
        } catch (Exception e) {
            log.info("[POST]'/user/update/' => user/list");
            return "redirect:/user/list";
        }
    }
}
