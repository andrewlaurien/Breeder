package mybreed.andrewlaurien.com.gamecocksheet.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.Date;

import mybreed.andrewlaurien.com.gamecocksheet.AddNote;
import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.MyEventDay;
import mybreed.andrewlaurien.com.gamecocksheet.NotePreview;
import mybreed.andrewlaurien.com.gamecocksheet.R;

import static android.app.Activity.RESULT_OK;


public class ScheduleFragment extends Fragment {

    View view;

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;
    private CalendarView mCalendarView;
    //private List<EventDay> mEventDays = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date date = new Date();

        mCalendarView = view.findViewById(R.id.calendarView);

        try {
            mCalendarView.setDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                previewNote(eventDay);
            }
        });


        setEventList();

    }

    public void setEventList() {
        mCalendarView.setEvents(MainActivity.mEventDays);
    }

    private void addNote() {
        Intent intent = new Intent(getContext(), AddNote.class);
        startActivityForResult(intent, ADD_NOTE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE) {
            if (resultCode == RESULT_OK) {
                MyEventDay myEventDay = data.getParcelableExtra(RESULT);
                MainActivity.mEventDays.add(myEventDay);
                CommonFunc.setPreferenceObject(getContext(), MainActivity.mEventDays, "Events");
                MainActivity.mDataBase.getReference("Events").child(MainActivity.user.getMobile()).setValue(MainActivity.mEventDays);

                MainActivity.db.saveNote(MainActivity.db, myEventDay);
                setEventList();
            }
        }

    }

    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(getContext(), NotePreview.class);
        if (eventDay instanceof MyEventDay) {
            intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        startActivity(intent);
    }

}
