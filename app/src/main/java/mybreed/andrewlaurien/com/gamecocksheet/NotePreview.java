package mybreed.andrewlaurien.com.gamecocksheet;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mybreed.andrewlaurien.com.gamecocksheet.Fragment.ScheduleFragment;
import mybreed.andrewlaurien.com.gamecocksheet.Model.MyEventDay;

public class NotePreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        TextView note = (TextView) findViewById(R.id.note);
        if (intent != null) {
            Object event = intent.getParcelableExtra(ScheduleFragment.EVENT);
            Log.d("Note", "Here" );
            if (event instanceof MyEventDay) {
                MyEventDay myEventDay = (MyEventDay) event;
                Log.d("Note", myEventDay.getNote());
                getSupportActionBar().setTitle(getFormattedDate(myEventDay.getCalendar().getTime()));
                note.setText(myEventDay.getNote());
                return;
            }
            if (event instanceof EventDay) {
                EventDay eventDay = (EventDay) event;
                getSupportActionBar().setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
            }
        }
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}