package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.FileDto;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, ModelMapper modelMapper) {
        this.fileService = fileService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, @SessionAttribute("user") User user,
                             final RedirectAttributes redirectAttributes) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileService.isDuplicateFile(filename)) {
            redirectAttributes.addFlashAttribute("uploadError", "A file with the name '" + filename + "' already exists.");
        } else {
            fileService.createFile(new File(null, filename, file.getContentType(), Long.toString(file.getSize()), user.getUserId(), file.getBytes()));
            redirectAttributes.addFlashAttribute("uploadSuccess", "File was successfully uploaded.");
        }
        return "redirect:home";
    }

    @DeleteMapping("/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("successMsg", "Your file has now been deleted!");
        return "fragments/messages::successMsg";
    }

    @GetMapping
    public ResponseEntity downloadFile(@RequestParam("filename") String filename, Model model) {
        FileDto fileDto = modelMapper.map(fileService.getFileByName(filename), FileDto.class);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(fileDto.getFileData());
    }
}
