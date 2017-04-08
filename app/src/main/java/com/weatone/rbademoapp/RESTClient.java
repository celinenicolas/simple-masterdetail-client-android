package com.weatone.rbademoapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Celine Nicolas
 * @version 1.0.1, 2017-03-16
 * @since 1.0.0
 */

public class RESTClient extends AsyncTask<String, Void, RESTClient.Result> {

    private DownloadCallback<String> mCallback;

    static class Result {

        public String mResultValue;
        public Exception mException;
        public Result(String resultValue) {
            mResultValue = resultValue;
        }
        public Result(Exception exception) {
            mException = exception;
        }

    }

    public RESTClient(DownloadCallback<String> callback) {
        setCallback(callback);
    }

    public void setCallback(DownloadCallback<String> callback) {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        // TODO: check connectivity
    }

    @Override
    protected RESTClient.Result doInBackground(String... urls) {
        Result result = null;
        if (!isCancelled() && urls != null && urls.length > 0) {
            String urlString = urls[0];
            Log.d("TEST", "url:" + urlString);
            try {
                URL url = new URL(urlString);
                String resultString = downloadUrl(url);
                if (resultString != null) {
                    result = new Result(resultString);
                } else {
                    Log.d("TEST", "error downloading...");
                    throw new IOException("No response received.");
                }
            } catch(Exception e) {
                result = new Result(e);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (result != null && mCallback != null) {
            if (result.mException != null) {
                //mCallback.onError(result.mException.getMessage());
            } else if (result.mResultValue != null) {
                mCallback.onData(result.mResultValue);
            }
            //mCallback.onDownloadComplete();
        }
    }

    @Override
    protected void onCancelled(Result result) {
    }

    private String downloadUrl(URL url) throws IOException, Exception {
        Log.d("TEST", "downloading...");
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestMethod("GET");

            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP Error Code:" + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                result = readBufferedStream(stream);
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private String readBufferedStream(InputStream stream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"utf-8"),8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        stream.close();
        return sb.toString();
    }
}