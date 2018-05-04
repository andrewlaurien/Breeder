package mybreed.andrewlaurien.com.gamecocksheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.applandeo.materialcalendarview.CalendarView;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.Fragment.ScheduleFragment;
import mybreed.andrewlaurien.com.gamecocksheet.Model.MyEventDay;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CalendarView datePicker = (CalendarView) findViewById(R.id.datePicker);
        Button button = (Button) findViewById(R.id.addNoteButton);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                Log.d("Date", datePicker.getSelectedDate().getTime().toString());
                Log.d("Date", CommonFunc.getDateOnly(datePicker.getSelectedDate().getTime()));
                //Log.d("Date", DateFormat.getDateInstance(DateFormat.SHORT).format(datePicker.getSelectedDate().getTime()));
                String date = CommonFunc.getDateOnly(datePicker.getSelectedDate().getTime());
                MyEventDay myEventDay = new MyEventDay(datePicker.getSelectedDate(),
                        R.drawable.ic_note, noteEditText.getText().toString(), date);
                returnIntent.putExtra(ScheduleFragment.RESULT, myEventDay);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
