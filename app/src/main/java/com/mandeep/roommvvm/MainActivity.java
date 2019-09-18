package com.mandeep.roommvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Notes");

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditAddNotesActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnClickListener() {
            @Override
            public void onClicked(Note note) {
                Intent intent = new Intent(MainActivity.this, EditAddNotesActivity.class);
                intent.putExtra(EditAddNotesActivity.TITLE, note.getTitle());
                intent.putExtra(EditAddNotesActivity.DESCRIPTION, note.getDescription());
                intent.putExtra(EditAddNotesActivity.PRIORITY, note.getPriority());
                intent.putExtra(EditAddNotesActivity.NOTE_ID, note.getId());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                String title = data.getStringExtra(EditAddNotesActivity.TITLE);
                String description = data.getStringExtra(EditAddNotesActivity.DESCRIPTION);
                int priority = data.getIntExtra(EditAddNotesActivity.PRIORITY, 1);

                noteViewModel.insert(new Note(title, description, priority));
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                int id = data.getIntExtra(EditAddNotesActivity.NOTE_ID, -1);
                if (id == -1) {
                    Toast.makeText(this, "Can't Update this Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                String title = data.getStringExtra(EditAddNotesActivity.TITLE);
                String description = data.getStringExtra(EditAddNotesActivity.DESCRIPTION);
                int priority = data.getIntExtra(EditAddNotesActivity.PRIORITY, 1);

                Note note = new Note(title, description, priority);
                note.setId(id);

                noteViewModel.update(note);

                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAllNotes) {
            noteViewModel.deleteAll();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
