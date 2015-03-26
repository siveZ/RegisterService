package com.registerservic.app.request.manager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Artyom on 3/20/2015.
 */
public class RequestManager {

    private String baseUrl = "http://myservice.esy.es/index.php/api/";
    private HttpClient httpClient;
    private HttpResponse httpResponse;


    public RequestManager(){
        httpClient = new DefaultHttpClient();
    }

    public String postData(String segment, List<NameValuePair> nameValuePairs) {
        HttpPost httpPost = new HttpPost(baseUrl + segment);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return null;
    }

    public String getRequest(String segment){
        HttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(baseUrl + segment);
            httpResponse = httpClient.execute(httpGet);
            return  EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            return null;
        }

    }
}
