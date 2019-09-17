package com.mandeep.roommvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private List<Note> noteData = new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteData.get(position);
        holder.priorityTv.setText(String.valueOf(note.getPriority()));
        holder.titleTv.setText(note.getTitle());
        holder.descriptionTv.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }

    public void setNotes(List<Note> noteList){
        this.noteData = noteList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView priorityTv, titleTv, descriptionTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            priorityTv = itemView.findViewById(R.id.priority);
            titleTv = itemView.findViewById(R.id.title);
            descriptionTv = itemView.findViewById(R.id.description);
        }
    }
}
