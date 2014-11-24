package com.rhefew.cocdrive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLHandshakeException;

public class JSONParser {

  
  public JSONParser() {
  }

    public String get(String stringUrl){

        String resultText = "";
        try{
            URL url;
            url = new URL(stringUrl);

            String charset = "UTF-8";
            //IT MAKES SSL CHECK!! :D
            URLConnection connection;


            connection = url.openConnection();
            connection.addRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);


            InputStream response = null;
            try{
                response = connection.getInputStream();
            }catch(SSLHandshakeException e){
                e.printStackTrace();
            }

            String line;
            char[] buffer = new char[8064];
            BufferedReader reader = new BufferedReader(new InputStreamReader(response), buffer.length);

            while ((line = reader.readLine()) != null) {
                resultText+=line;
            }
            reader.close();
            response.close();

            return resultText;

        } catch (MalformedURLException e) {
            resultText = "error";
            e.printStackTrace();
        } catch (IOException e) {
            resultText = "error";
            e.printStackTrace();
        }
        return resultText;
    }

    public String put(String stringUrl){

        String resultText = "";
        try{
            URL url;
            url = new URL(stringUrl);

            String charset = "UTF-8";
            //IT MAKES SSL CHECK!! :D
            URLConnection connection;

            connection = url.openConnection();
            connection.addRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

            InputStream response = null;
            try{
                response = connection.getInputStream();
            }catch(SSLHandshakeException e){
                e.printStackTrace();
            }

            if ("gzip".equals(connection.getContentEncoding())) {
                response = new GZIPInputStream(response);
            }

            String line;
            char[] buffer = new char[8064];
            BufferedReader reader = new BufferedReader(new InputStreamReader(response), buffer.length);

            while ((line = reader.readLine()) != null) {
                resultText+=line;
            }
            reader.close();
            response.close();

            return resultText.split("\t")[0];

        } catch (MalformedURLException e) {
            resultText = "error";
            e.printStackTrace();
        } catch (IOException e) {
            resultText = "error";
            e.printStackTrace();
        }
        return resultText;
    }

    public JSONArray getJSONArray(String stringUrl) throws IOException, JSONException {
        // Making HTTP request
        try {
            String result = get(stringUrl);
            JSONArray jsonArray = new JSONArray(result);
            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
	}

    public JSONArray getJSONArray(String stringUrl, String jsonObject) throws IOException, JSONException {
        // Making HTTP request
        try {
            String result = get(stringUrl);
            JSONArray jsonArray = new JSONObject(result).getJSONArray(jsonObject);
            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject getJSON(String stringUrl) throws IOException, JSONException {
        // Making HTTP request
        try {

            JSONObject json = new JSONObject(get(stringUrl));
            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}








