package net.harithproperties.jogapps;

/**
 * Created by Pika on 3/2/2018.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //define all the constants
    static String DATABASE_NAME="joggingdb";
    public static final String TABLE_NAME="jogging";
    //these are the lit of fields in the table
    public static final String JOGID="jogid";

    public static final String DISTANCEKM="distance";
    public static final String SPEED="speed";
    public static final String TIME="time";
    public static final String DATE="date";
    public static final String DURATION="duration";


    public DBHelper(Context context) {
        //create the database
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+JOGID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+DATE+" TEXT, "+TIME+" TEXT," +
                " "+DISTANCEKM+" TEXT,"+SPEED+" TEXT,"+DURATION+" TEXT)";
        db.execSQL(CREATE_TABLE);
        //populate dummy data
        db.execSQL("INSERT INTO jogging (jogid, date, time, distance, speed, duration) VALUES('1', '3/7/2018', '18:00','5km','5v', '1min');");
        db.execSQL("INSERT INTO jogging (jogid, date, time, distance, speed, duration) VALUES('2', '3/2/2018', '19:00','5km','5v', '1min');");
        db.execSQL("INSERT INTO jogging (jogid, date, time, distance, speed, duration) VALUES('3', '4/2/2018', '12:00','5km','5v', '1min');");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade remove the existing table, and recreate and populate new data
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }



}
