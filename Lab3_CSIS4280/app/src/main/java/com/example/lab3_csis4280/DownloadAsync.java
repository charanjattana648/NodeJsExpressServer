package com.example.lab3_csis4280;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadAsync extends AsyncTask<String, Integer, String> {
    private OnEventListener<String> theCallBack;
    public DownloadAsync(OnEventListener theCallBack)
    {
        this.theCallBack=theCallBack;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... data) {
        String site=data[0];
        String params=data[1];
        String result=getRemoteData(site,params);
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }



    private String getRemoteData(String site, String params) {
        StringBuilder sb=new StringBuilder();
        HttpURLConnection c=null;
        String dataFromServer="";
        try{
            URL u =new URL(site);
            c= (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoInput(true);
            c.setDoOutput(true);
            OutputStreamWriter writer=new OutputStreamWriter(c.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();
            Log.d(this.getClass().getName(),"Connecting");
            c.connect();
            int status=c.getResponseCode();
            Log.d("code",status+"");
            switch (status){
                case 200:
                case 201:
                    BufferedReader br=new BufferedReader(new InputStreamReader(c.getInputStream()));
                    String line;
                    while ((line=br.readLine())!=null)
                    {
                        sb.append(line+"\n");
                    }
                    br.close();
                    dataFromServer=sb.toString();
                    break;
                case  404:
                    return "Can't find";
            }
        }catch (Exception ex)
        {
            return ex.toString();
        }finally {
            if(c!=null)
            {
                try{
                    c.disconnect();
                }catch (Exception ex)
                {
                    return ex.toString();
                }
            }
        }


        return dataFromServer;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("onPostExecute", result);
        theCallBack.onSuccess(result);
    }
}
