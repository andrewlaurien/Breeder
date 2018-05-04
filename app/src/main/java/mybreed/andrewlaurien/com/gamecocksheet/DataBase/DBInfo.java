package mybreed.andrewlaurien.com.gamecocksheet.DataBase;

import android.provider.BaseColumns;

/**
 * Created by andrewlaurienrsocia on 28/03/2018.
 */

public class DBInfo implements BaseColumns {

    public static final int DB_version = 1;
    public static final String DB_Name = "record_sheet";

    //Tables
    public static final String TBL_Schedule = "Schedule";

    //Fields
    public static final String DateSched = "DateSched";
    public static final String Date = "Date";
    public static final String Note = "Note";
    public static final String Status = "Status";


    //CREATE QUERY
    public static final String CREATE_SCHEDULE = "CREATE TABLE " + TBL_Schedule
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + DateSched
            + " TEXT," + DBInfo.Note + " TEXT," + Status
            + " TEXT," + DBInfo.Date + " TEXT)";
}
