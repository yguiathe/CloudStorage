package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final ModelMapper modelMapper;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(ModelMapper modelMapper, CredentialService credentialService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.modelMapper = modelMapper;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String getCredentialsByUser(@SessionAttribute("user") User user, Model model) {
        List<CredentialDto> myCredentials = credentialService.getUserCredentials(user).stream().map(credential -> modelMapper.map(credential, CredentialDto.class)).collect(Collectors.toList());
        if(!myCredentials.isEmpty()) {
            for (CredentialDto credentialDto : myCredentials) {
                credentialDto.setClearPassword(encryptionService.decryptValue(credentialDto.getPassword(), credentialDto.getKey()));
            }
        }
        model.addAttribute("myCredentials", myCredentials);
        return "fragments/lists::credentials-list";
    }

    @PostMapping()
    public String addCredential(@ModelAttribute CredentialDto credentialDto, Model model, @SessionAttribute("user") User user) {
        Credential credential = modelMapper.map(credentialDto, Credential.class);
        credential.setUserId(user.getUserId());
        String fragment;

        if(credential.getCredentialId() != null) {
            credentialService.updateCredentials(credential);
            model.addAttribute("successMsg", "Credential was successfully updated.");
            fragment = "fragments/messages::successMsg";
        } else {
            int rowsAdded = credentialService.createCredential(credential);
            if(rowsAdded < 0) {
                model.addAttribute("errorMsg", "Couldn't add the credential - Please contact your system administrator for more information.");
                fragment = "fragments/messages::errorMsg";
            } else {
                model.addAttribute("successMsg", "Credential was successfully added");
                fragment = "fragments/messages::successMsg";
            }
        }

        return fragment;
    }

    @DeleteMapping("/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);
        model.addAttribute("successMsg", "Credential has been successfully deleted.");
        return "fragments/messages::successMsg";
    }

}
