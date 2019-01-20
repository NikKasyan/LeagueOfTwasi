package at.saith.twasi.lot.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class JsonUrlParser {
    private HttpURLConnection urlConnection;
    private Map<String, List<String>> headerFields;

    public JsonUrlParser(String urlString) throws MalformedURLException {
        this(new URL(urlString));
    }

    public JsonUrlParser(URL url) {
        try {
            this.urlConnection = (HttpURLConnection) url.openConnection();
            this.headerFields = urlConnection.getHeaderFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject parseUrlAsJsonObject() {
        return parseUrlAsJsonElement().getAsJsonObject();
    }

    public JsonElement parseUrlAsJsonElement() {
        return new JsonParser().parse(parseUrl());
    }


    private String parseUrl() {
        BufferedReader reader = null;
        InputStream stream = null;
        try {
            stream = urlConnection.getInputStream();
        } catch (IOException exception) {
        }
        if (stream == null) {
            stream = urlConnection.getErrorStream();
        }
        try {

            reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "{}";
    }

    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }
}
