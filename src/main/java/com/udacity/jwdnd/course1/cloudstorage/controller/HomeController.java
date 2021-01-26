package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.FileDto;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
@SessionAttributes("user")
public class HomeController {

    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(ModelMapper modelMapper, FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping()
    public String getHomePage(@ModelAttribute("user") User user, Model model) {
        List<FileDto> myFiles = fileService.getUserFiles(user).stream().map(file -> modelMapper.map(file, FileDto.class)).collect(Collectors.toList());
        model.addAttribute("myFiles", myFiles);
        model.addAttribute("noteDto", new NoteDto(null, "Good Title", "Note Description", null));
        return "home";
    }

    @ModelAttribute("user")
    public User getUserId(Authentication authentication) {
        return userService.getUser(authentication.getName());
    }

}
