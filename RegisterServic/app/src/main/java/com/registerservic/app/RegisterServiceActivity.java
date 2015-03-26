package com.registerservic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.registerservic.app.request.manager.RequestManager;
import com.registerservic.app.utils.ApiRequests;
import com.registerservic.app.utils.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class RegisterServiceActivity extends Activity {

    private EditText nameEditText;
    private EditText serviceTypeEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private EditText descEditText;
    private Button registerBtn;
    private RequestManager requestManager;
    private List<NameValuePair> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_service);
        requestManager = new RequestManager();
        initView();


    }

    public void initView(){
        nameEditText = (EditText) findViewById(R.id.name_id);
        serviceTypeEditText = (EditText) findViewById(R.id.service_type_id);
        addressEditText = (EditText) findViewById(R.id.address_id);
        phoneEditText = (EditText) findViewById(R.id.phone_id);
        descEditText = (EditText) findViewById(R.id.desc_id);
        registerBtn = (Button) findViewById(R.id.register_btn);
    }

    public void registerBtnClick(View view){
        if(Utils.isOnline()) {
            String name = nameEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String service_type = serviceTypeEditText.getText().toString();
            String description = descEditText.getText().toString();
            if (!Utils.isEmpty(name) && !Utils.isEmpty(address) && !Utils.isEmpty(phone)
                    && !Utils.isEmpty(service_type) && !Utils.isEmpty(description)) {
                list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("name", name));
                list.add(new BasicNameValuePair("address", address));
                list.add(new BasicNameValuePair("phone", phone));
                list.add(new BasicNameValuePair("service_type", service_type));
                list.add(new BasicNameValuePair("description", description));
                new SendRequest().execute();
            } else {
                Toast.makeText(RegisterServiceActivity.this, getResources().getString(R.string.error_required), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(RegisterServiceActivity.this, getResources().getString(R.string.internet_error), Toast.LENGTH_LONG).show();

        }
    }

    class SendRequest extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            requestManager.postData(ApiRequests.REGISTER_SERVICE, list);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(RegisterServiceActivity.this, MainActivity.class));
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
