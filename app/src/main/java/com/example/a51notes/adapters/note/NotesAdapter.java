package com.example.a51notes.adapters.note;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a51notes.R;
import com.example.a51notes.pojos.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simonyan51 on 6/4/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notesList;
    private OnItemNoteClickListener listener;
    private OnItemNoteLongClickListener longListener;

    public NotesAdapter(OnItemNoteClickListener listener, OnItemNoteLongClickListener longListener) {

        notesList = new ArrayList<>();
        this.listener = listener;
        this.longListener = longListener;

    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, final int position) {

        final Note currentNote = notesList.get(position);

        holder.title.setText(currentNote.getTitle());
        holder.desc.setText(currentNote.getDescription());
        holder.createDate.setText(currentNote.getCreateDate().toString());
        holder.alarmDate.setText(currentNote.getAlarmDate() != null? currentNote.getAlarmDate().toString() :  "");
        holder.important.setChecked(currentNote.isImportant());
        holder.color.setBackgroundColor(currentNote.getColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, currentNote, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longListener.onItemLongClick(v, currentNote, position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        private Note note;
        private int position;
        private TextView title;
        private TextView desc;
        private TextView createDate;
        private TextView alarmDate;
        private CheckBox important;
        private View color;

        public NotesViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.note_item_title);
            desc = (TextView) itemView.findViewById(R.id.note_item_desc);
            createDate = (TextView) itemView.findViewById(R.id.note_item_create_date);
            alarmDate = (TextView) itemView.findViewById(R.id.note_item_alarm_date);
            important = (CheckBox) itemView.findViewById(R.id.note_item_important);
            color = (View) itemView.findViewById(R.id.note_item_color);
        }
    }


    public static interface OnItemNoteClickListener {
        void onItemClick(View v, Note note, int position);
    }

    public static interface OnItemNoteLongClickListener {
        void onItemLongClick(View v, Note note, int position);
    }

}
