package mybreed.andrewlaurien.com.gamecocksheet.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

/**
 * Created by andrewlaurienrsocia on 26/03/2018.
 */

public class MyEventDay extends EventDay implements Parcelable {

    private String note;
    private String mDate;

    public MyEventDay(Calendar day, int imageResource, String mnote, String mdate) {
        super(day, imageResource);
        note = mnote;
        mDate = mdate;
    }

    public String getDate() {
        return mDate;
    }

    public String getNote() {
        return note;
    }

    private MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        note = in.readString();
        mDate = in.readString();
    }

    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }

        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(note);
        parcel.writeString(mDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}