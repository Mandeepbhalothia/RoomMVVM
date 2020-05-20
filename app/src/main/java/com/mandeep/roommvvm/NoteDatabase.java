package com.mandeep.roommvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
abstract class NoteDatabase extends androidx.room.RoomDatabase {

    private static NoteDatabase instance;

    abstract NoteDao getNoteDao();

    static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                   NoteDatabase.class, "note_database" ).
                    fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AttachToDb(instance).execute();
        }
    };

    private static class AttachToDb extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
        private AttachToDb(NoteDatabase noteDatabase){
            this.noteDao = noteDatabase.getNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insertNote(new Note("Title 1 ", "Description 1 ",1));
            noteDao.insertNote(new Note("Title 2 ", "Description 2 ",2));
            noteDao.insertNote(new Note("Title 3 ", "Description 3 ",3));
            return null;
        }
    }
}
