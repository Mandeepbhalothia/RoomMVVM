package com.mandeep.roommvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class EditAddNotesActivity extends AppCompatActivity {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";
    public static final String NOTE_ID = "noteId";
    private EditText titleEditText, descriptionEditText;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        if (getIntent().hasExtra(NOTE_ID)) {
            setTitle("Edit Note");
            titleEditText.setText(getIntent().getStringExtra(TITLE));
            descriptionEditText.setText(getIntent().getStringExtra(DESCRIPTION));
            numberPicker.setValue(getIntent().getIntExtra(PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Title and description are required fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(TITLE, title);
        data.putExtra(DESCRIPTION, description);
        data.putExtra(PRIORITY, priority);
        int id = getIntent().getIntExtra(NOTE_ID, -1);
        if (id != -1) {
            data.putExtra(NOTE_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveMenu) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
