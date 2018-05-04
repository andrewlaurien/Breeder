package mybreed.andrewlaurien.com.gamecocksheet.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mybreed.andrewlaurien.com.gamecocksheet.Model.MyEventDay;

/**
 * Created by andrewlaurienrsocia on 28/03/2018.
 */

public class DBHelper extends SQLiteOpenHelper {


    Context mcontext;

    public DBHelper(Context context) {
        super(context, DBInfo.DB_Name, null, DBInfo.DB_version);
        mcontext = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(DBInfo.CREATE_SCHEDULE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveNote(DBHelper db, MyEventDay eventDay) {
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBInfo.Date, eventDay.getDate());
        cv.put(DBInfo.DateSched, eventDay.getCalendar().toString());
        cv.put(DBInfo.Note, eventDay.getNote());
        sql.insert(DBInfo.TBL_Schedule, null, cv);

    }


    public Cursor getNotes(DBHelper db) {
        SQLiteDatabase sql = db.getReadableDatabase();

        String query = "Select * From " + DBInfo.TBL_Schedule + " Group By Date";

        Cursor c = sql.rawQuery(query, null);

        return c;

    }
}
