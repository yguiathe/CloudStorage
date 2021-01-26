package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotesByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle} AND userid = #{userId}")
    Note getNoteByTitle(String noteTitle, Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNoteById(Integer noteId);

    @Update("UPDATE NOTES SET NOTETITLE = #{noteTitle}, NOTEDESCRIPTION = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Note note);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);
}
