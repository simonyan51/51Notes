package com.example.a51notes.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a51notes.R;
import com.example.a51notes.adapters.note.NotesAdapter;
import com.example.a51notes.pojos.Note;
import com.example.a51notes.utils.note.FileNoteStorage;
import com.example.a51notes.utils.note.NoteStorage;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class NotesListFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ".a51notes.fragments.NotesListFragment";

    private NoteStorage noteStorage;
    private List<Note> notesList;
    private NotesAdapter.OnItemNoteClickListener listener;
    private NotesAdapter.OnItemNoteLongClickListener longListener;
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private FloatingActionButton createButton;
    private FragmentTransaction transaction;

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notes_list, container, false);

        getActivity().setTitle("Notes List");

        notesList = new ArrayList<>();

        implementNoteLongClickListener();
        implementNoteClickListener();

        noteStorage = new FileNoteStorage();

        createButton = (FloatingActionButton) v.findViewById(R.id.fragment_notes_list_add_note);

        transaction = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        createButton.setOnClickListener(this);


        getNewNote();

        createNotesList(v);

        return v;
    }


    private void createNotesList(View v) {

        recyclerView = (RecyclerView) v.findViewById(R.id.fragment_notes_list_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        notesAdapter = new NotesAdapter(listener, longListener);

        noteStorage.findAllNotes(new NoteStorage.NotesFoundListener() {
            @Override
            public void onNotesFound(List<Note> notes) {
                notesList = notes;
            }
        });

        notesAdapter.setNotesList(notesList);

        recyclerView.setAdapter(notesAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_notes_list_add_note:
                transaction.replace(R.id.activity_main_container, new CreateNoteFragment(), CreateNoteFragment.TAG);
                break;
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getNewNote() {
        if (getArguments() == null) {
            return;
        }
        Note newNote = (Note) getArguments().getSerializable("newNote");
        if (newNote != null) {
            noteStorage.createNote(newNote, new NoteStorage.NoteListener() {
                @Override
                public void onNote(Note note) {
                    notesList.add(note);
                    Toast.makeText(getActivity(), "New Note Was Created!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            return;
        }
    }

    private void implementNoteClickListener() {
        listener = new NotesAdapter.OnItemNoteClickListener() {
            @Override
            public void onItemClick(View v, Note note, int position) {

                NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();

                Parcelable currentNote = Parcels.wrap(note);

                Bundle args = new Bundle();
                args.putParcelable("currentNote", currentNote);

                noteDetailsFragment.setArguments(args);


                transaction.replace(R.id.activity_main_container, noteDetailsFragment, NoteDetailsFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        };
    }


    private void implementNoteLongClickListener() {
        longListener = new NotesAdapter.OnItemNoteLongClickListener() {
            @Override
            public void onItemLongClick(View v, final Note note, int position) {
          createPropertiesDialog(note);
            }
        };
    }


    private void createPropertiesDialog(final Note note) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        View properties = getActivity().getLayoutInflater().inflate(R.layout.properties_layout, null);

        alertDialogBuilder.setView(properties);
        final AlertDialog dialog = alertDialogBuilder.create();

        properties.findViewById(R.id.properties_layout_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote(note);
                dialog.cancel();
            }
        });

        properties.findViewById(R.id.properties_layout_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(note);
                dialog.cancel();
            }
        });

        properties.findViewById(R.id.properties_layout_alarm_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setAlarmDate(null);
                dialog.cancel();
            }
        });

        properties.findViewById(R.id.properties_layout_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    }

    private void editNote(Note note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable("editNote", Parcels.wrap(note));
        editNoteFragment.setArguments(args);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_container, editNoteFragment).addToBackStack(null).commit();
    }

    private void deleteNote(final Note note) {
        noteStorage.deleteNote(note, new NoteStorage.NoteDeleteListener() {
            @Override
            public void onNoteDeleted(boolean successful) {
                if (successful) {
                    notesAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Note Succesfully Deleted!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (this.getArguments() != null) {this.getArguments().clear();}
    }
}
