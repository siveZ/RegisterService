package com.registerservic.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Artyom on 3/20/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "services.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_SERVICES = "services";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVICE_TYPE = "service_type";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PHONE = "phone";

    public static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVICES +
            "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
             COLUMN_NAME + " TEXT NOT NULL, " +
             COLUMN_ADDRESS + " TEXT NOT NULL, " +
             COLUMN_SERVICE_TYPE + " TEXT NOT NULL, " +
             COLUMN_PHONE + " TEXT NOT NULL, " +
             COLUMN_DESCRIPTION + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);
    }

}
