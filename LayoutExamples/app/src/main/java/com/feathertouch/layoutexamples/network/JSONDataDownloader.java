package com.feathertouch.layoutexamples.network;

import android.accounts.NetworkErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class JSONDataDownloader {

    String url;
    Map<String, String> headers;
    public JSONDataDownloader(String url, Map<String, String>headers) {
        this.url = url;
        this.headers = headers;
    }

    public String download() throws NetworkErrorException, MalformedURLException, IOException {

        // Create GET HTTP Request with url, headers
        HttpURLConnection urlConnection = getConnection();

        // Connect to network with given HTTP request

        // Open communications link (network traffic occurs here).
        urlConnection.connect();


        int responseCode = urlConnection.getResponseCode();
        if (responseCode != HttpsURLConnection.HTTP_OK) {
            throw new NetworkErrorException("HTTP error code: " + responseCode);
        }

        // Process the response data

        String responseData = getResponseString(urlConnection);


        // return data as string

        return  responseData;
    }


    private HttpURLConnection getConnection() throws MalformedURLException, IOException {
        URL downloadURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();
        // Timeout for reading InputStream arbitrarily set to 3000ms.
        connection.setReadTimeout(3000);

        // Timeout for connection.connect() arbitrarily set to 3000ms.
        connection.setConnectTimeout(3000);

        // For this use case, set HTTP method to GET.
        connection.setRequestMethod("GET");

        // Already true by default but setting just in case; needs to be true since this request
        // is carrying an input (response) body.
        connection.setDoInput(true);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        return  connection;
    }


    private String getResponseString(HttpURLConnection  connection) throws  IOException, UnsupportedEncodingException {
        // Retrieve the response body as an InputStream.
        InputStream stream = null;
        stream = connection.getInputStream();
        String result = null;
        if (stream != null) {
            // Converts Stream to String with max length of 500.
            result = readStream(stream, 500);
        }

        return result;
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1)) {
            buffer.append(rawBuffer, 0, readSize);
        }
        return buffer.toString();
    }
}
