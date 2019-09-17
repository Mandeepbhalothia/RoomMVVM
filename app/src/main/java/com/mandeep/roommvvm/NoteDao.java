package com.mandeep.roommvvm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    public void insertNote(Note note);

    @Update
    public void updateNote(Note note);

    @Delete
    public void delete(Note note);

    // WE CAN USE notes_table(table name) AS WELL AS NOTES_TABLE
    @Query("DELETE FROM NOTES_TABLE ")
    public void deleteAllNotes();

    @Query("SELECT * FROM NOTES_TABLE ORDER BY PRIORITY DESC")
    public LiveData<List<Note>> getAllNotes();
}
