package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNoteById(noteId);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public int createNote(Note note) {
        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    public Note getNotesByTitle(Integer userId, String title) {
        return noteMapper.getNoteByTitle(title, userId);
    }
}
