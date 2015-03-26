package com.registerservic.app.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.registerservic.app.db.DBHelper;
import com.registerservic.app.request.manager.RequestManager;
import com.registerservic.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Artyom on 3/20/2015.
 */
public class CustomContentProvider extends ContentProvider {

    DBHelper dbHelper = null;
    RequestManager requestManager = null;
    private static final int SERVICES = 1;
    private static final int SERVICE_ID = 2;
    private static final UriMatcher MATCHER;
    public static final String DEFAULT_SORT_ORDER = "_id";
    public static final Uri CONTENT_URI = Uri.parse("content://com.registerservic.app.CustomContentProvider");



    static{
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI("com.registerservic.app.CustomContentProvider",
                "services", SERVICES);
        MATCHER.addURI("com.registerservic.app.CustomContentProvider",
                "services/#", SERVICE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());

        return false;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
            SQLiteDatabase db = dbHelper.getWritableDatabase(); // helper = MyDatabaseHelper
            int count = db.delete(DBHelper.TABLE_SERVICES, null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 0; // number of rows affected
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long row = dbHelper.getWritableDatabase().insert(dbHelper.TABLE_SERVICES, "", contentValues);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        if(Utils.isOnline()) {
            requestManager = new RequestManager();
            new RequestAsynctask().execute();
        }
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(dbHelper.TABLE_SERVICES);
        String orderBy;

        if (TextUtils.isEmpty(s2)) {
            orderBy = DEFAULT_SORT_ORDER + " DESC";
        }
        else {
            orderBy = s2;
        }

        Cursor c =
                qb.query(dbHelper.getReadableDatabase(), strings, s,
                        strings2, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
        return(c);

    }


    class RequestAsynctask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... voids) {
            return requestManager.getRequest("getAllServices");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ContentValues values = new ContentValues();
            try {
                JSONArray jsonArray = new JSONArray(result);
                int lenght = jsonArray.length();
                if(lenght > 0){
                    delete(CONTENT_URI, null, null);
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String service_type = jsonObject.getString("service_type");
                    String address = jsonObject.getString("address");
                    String description = jsonObject.getString("description");
                    String phone = jsonObject.getString("phone");
                    values.put(dbHelper.COLUMN_NAME, name);
                    values.put(dbHelper.COLUMN_SERVICE_TYPE, service_type);
                    values.put(dbHelper.COLUMN_ADDRESS, address);
                    values.put(dbHelper.COLUMN_PHONE, phone);
                    values.put(dbHelper.COLUMN_DESCRIPTION, description);

                    insert(CONTENT_URI, values);

                }
                Log.i("Data Changed", "Data changed " + lenght);
                getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
