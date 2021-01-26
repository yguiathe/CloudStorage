package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final ModelMapper modelMapper;
    private final NoteService noteService;

    public NoteController(ModelMapper modelMapper, NoteService noteService) {
        this.modelMapper = modelMapper;
        this.noteService = noteService;
    }

    @GetMapping()
    public String getNotesByUser(@SessionAttribute("user") User user, Model model) {
        List<NoteDto> myNotes = noteService.getNotesByUserId(user.getUserId()).stream().map(note -> modelMapper.map(note, NoteDto.class)).collect(Collectors.toList());
        model.addAttribute("myNotes", myNotes);
        return "fragments/lists::notes-list";
    }

    @PostMapping()
    public String addNote(@ModelAttribute NoteDto noteDto, Model model, @SessionAttribute("user") User user) {
        Note note = modelMapper.map(noteDto, Note.class);
        note.setUserId(user.getUserId());
        String errorMsg = null;
        String fragment = null;

        if(noteService.getNotesByTitle(note.getUserId(), note.getNoteTitle()) != null) {
            errorMsg = "Can't add duplicate note - Please choose a different title for the note.";
            model.addAttribute("errorMsg", errorMsg);
            fragment = "fragments/messages::errorMsg";
        }

        if(errorMsg == null) {
            if (note.getNoteId() != null) {
                noteService.updateNote(note);
                model.addAttribute("successMsg", "Note was successfully updated.");
                fragment = "fragments/messages::successMsg";
            } else {
                int rowsAdded = noteService.createNote(note);
                if (rowsAdded < 0) {
                    model.addAttribute("errorMsg", "Couldn't add the note - Please contact your system administrator for more information.");
                    fragment = "fragments/messages::errorMsg";
                } else {
                    model.addAttribute("successMsg", "Note was successfully added");
                    fragment = "fragments/messages::successMsg";
                }
            }
        }

        return fragment;
    }

    @DeleteMapping("/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("successMsg", "Note has been successfully deleted.");
        return "fragments/messages::successMsg";
    }
}
