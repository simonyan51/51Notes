package com.example.a51notes.broadcasts;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;

import com.example.a51notes.R;
import com.example.a51notes.pojos.Note;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simonyan51 on 6/20/17.
 */

public class AlarmDateBroadcastReceiver extends BroadcastReceiver {

    private static final int NOTIFY_ID = 5;

    private List<Note> subscribedNotes;

    private Note currentNote;

    public AlarmDateBroadcastReceiver() {
        subscribedNotes = new ArrayList<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        currentNote = (Note) Parcels.unwrap(intent.getExtras().getParcelable("currentNote"));

        if (currentNote.getAlarmDate() == null) {
            return;
        }

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(uri)
                .setContentTitle(currentNote.getTitle().toString())
                .setContentText(currentNote.getDescription().toString().substring(0, 10))
                .setTicker(currentNote.getTitle().toString());

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFY_ID, builder.build());

    }

    public void add(Note note) {
        subscribedNotes.add(note);
    }
}
