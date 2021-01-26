package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public SignupController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute("userDto") UserDto userDto, Model model) {
        String signupError = null;
        String template = null;

        User user = modelMapper.map(userDto, User.class);

        if(!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if(signupError == null) {
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if(signupError == null) {
            model.addAttribute("signupSuccess", true);
            template = "login";
        } else {
            model.addAttribute("signupError", signupError);
            template = "signup";
        }

        return template;
    }
}
