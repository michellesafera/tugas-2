package com.example.michelle.kodeweb;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Michelle on 10/20/2017.
 */

public class ConnectInternet extends AsyncTaskLoader<String> {

    String result = null;
    String url=null;
    boolean cancel = false;

    public ConnectInternet(Context conn, String Url) {
        super(conn);
        url = Url;
    }

    @Override
    public String loadInBackground() {

        InputStream is = null;
        HttpURLConnection myconn = null;

        try{
            URL myUrl = new URL(url);
            myconn = (HttpURLConnection) myUrl.openConnection();
            myconn.setReadTimeout(10000);
            myconn.setConnectTimeout(20000);
            myconn.setRequestMethod("GET");
            myconn.connect();

            if(myconn.getResponseCode()==HttpURLConnection.HTTP_OK){
                is = myconn.getInputStream();
                if(is!=null){
                    BufferedReader mybuf = new BufferedReader(new InputStreamReader(is));
                    StringBuilder st= new StringBuilder();
                    String line="";

                    while((line = mybuf.readLine())!=null){
                        st.append(line+" \n");
                    }
                    return  st.toString();
                }
            }
            else{
                return "Error Response Code"+myconn.getResponseCode();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return "No Network Connection.\nCheck Your Internet Connection and Try again later";
        }
        finally {
            if(is!=null){
                try {
                    is.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            myconn.disconnect();
        }
        return null;
    }

    protected void onStartLoading(){
        if(result!=null || cancel){
            deliverResult(result);
        }
        else{
            forceLoad();
        }
    }

    public void deliveredResult(String data){
        super.deliverResult(data);
        result=data;
    }

    public void onCanceled(String data){
        super.onCanceled(data);
        cancel=true;
    }


}
