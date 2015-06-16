package br.com.elsonsofts.studiodassobrancelhas.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by eliasafe on 27/04/15.
 */
public class ConnectorJSON {
    public Boolean makeHttpRequest(String URL_WEBSERVICE,
                                   List<NameValuePair> params) {
        // Making HTTP request
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;

        String entityResponse = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(URL_WEBSERVICE);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response : ", entityResponse);
            return true;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
