package com.registerservic.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.registerservic.app.data.ServiceData;
import com.registerservic.app.utils.Tags;


public class ServiceDetailsActivity extends Activity {

    ServiceData serviceData;
    TextView nameTxt;
    TextView addressTxt;
    TextView phoneTxt;
    TextView serviceTypeTxt;
    TextView descTxt;
    ImageView collImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_details);
        initView();
        getIntentValues();
        setViewValues();
        collImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(serviceData.getPhone().trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
    }

    public void getIntentValues(){
        serviceData = new ServiceData();
        Intent intent = getIntent();
        serviceData.setName(intent.getStringExtra(Tags.NAME));
        serviceData.setAddress(intent.getStringExtra(Tags.ADDRESS));
        serviceData.setPhone(intent.getStringExtra(Tags.PHONE));
        serviceData.setService_type(intent.getStringExtra(Tags.SERVICE_TYPE));
        serviceData.setDescription(intent.getStringExtra(Tags.DESCRIPTION));
    }

    public void initView(){
        nameTxt = (TextView) findViewById(R.id.d_name_txt);
        addressTxt = (TextView) findViewById(R.id.d_address_txt);
        phoneTxt = (TextView) findViewById(R.id.d_phone_txt);
        serviceTypeTxt = (TextView) findViewById(R.id.d_service_type_txt);
        descTxt = (TextView) findViewById(R.id.d_desc_txt);
        collImageView = (ImageView) findViewById(R.id.coll_img_id);
    }

    public void setViewValues(){
        nameTxt.setText(serviceData.getName());
        phoneTxt.setText(serviceData.getPhone());
        addressTxt.setText(serviceData.getAddress());
        serviceTypeTxt.setText(serviceData.getService_type());
        descTxt.setText(serviceData.getDescription());
    }


}
