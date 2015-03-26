package com.registerservic.app;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.registerservic.app.data.ServiceData;
import com.registerservic.app.db.DBHelper;
import com.registerservic.app.provider.CustomContentProvider;
import com.registerservic.app.utils.Tags;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Button registerBtn;
    private ListView listView;
    private SimpleCursorAdapter mAdapter;
    private static final String[] PROJECTION = new String[]{DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NAME, DBHelper.COLUMN_SERVICE_TYPE, DBHelper.COLUMN_ADDRESS, DBHelper.COLUMN_PHONE, DBHelper.COLUMN_DESCRIPTION};
    private static final int to[] = {0, R.id.service_name_txt, R.id.service_type_txt, R.id.service_address_txt, R.id.service_phone_txt, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        mAdapter = new SimpleCursorAdapter(this, R.layout.row, null, PROJECTION, to, 0);
        listView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().enableDebugLogging(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
              ServiceData serviceData = getDataFromCursor(cursor);
              if(null != serviceData) {
                  Intent intent = new Intent(MainActivity.this, ServiceDetailsActivity.class);
                  intent.putExtra(Tags.NAME, serviceData.getName());
                  intent.putExtra(Tags.SERVICE_TYPE, serviceData.getService_type());
                  intent.putExtra(Tags.ADDRESS, serviceData.getAddress());
                  intent.putExtra(Tags.PHONE, serviceData.getPhone());
                  intent.putExtra(Tags.DESCRIPTION, serviceData.getDescription());
                  startActivity(intent);
              }
            }
        });
    }

    public void initView() {
        registerBtn = (Button) findViewById(R.id.register_service_btn);
        listView = (ListView) findViewById(android.R.id.list);
    }

    public void registerServiceBtnClick(View view) {
        startActivity(new Intent(MainActivity.this, RegisterServiceActivity.class));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return (new CursorLoader(this, CustomContentProvider.CONTENT_URI,
                PROJECTION, null, null, null));
    }

    public ServiceData getDataFromCursor(Cursor cursorSource) {

    if(null != cursorSource){
        ServiceData serviceData = new ServiceData();
                String name = cursorSource.getString(cursorSource.getColumnIndex(DBHelper.COLUMN_NAME));
                String serviceType = cursorSource.getString(cursorSource.getColumnIndex(DBHelper.COLUMN_SERVICE_TYPE));
                String address = cursorSource.getString(cursorSource.getColumnIndex(DBHelper.COLUMN_ADDRESS));
                String phone = cursorSource.getString(cursorSource.getColumnIndex(DBHelper.COLUMN_PHONE));
                String desc = cursorSource.getString(cursorSource.getColumnIndex(DBHelper.COLUMN_DESCRIPTION));
                serviceData.setName(name);
                serviceData.setAddress(address);
                serviceData.setPhone(phone);
                serviceData.setService_type(serviceType);
                serviceData.setDescription(desc);
            return serviceData;
         }
        return null;
    }


}
