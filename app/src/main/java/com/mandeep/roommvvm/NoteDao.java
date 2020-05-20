package com.mandeep.roommvvm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void delete(Note note);

    // WE CAN USE notes_table(table name) AS WELL AS NOTES_TABLE
    @Query("DELETE FROM NOTES_TABLE ")
    void deleteAllNotes();

    @Query("SELECT * FROM NOTES_TABLE ORDER BY PRIORITY DESC")
    LiveData<List<Note>> getAllNotes();
}
