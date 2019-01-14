package at.saith.twasi.lot.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class JsonUrlParser {
    public JsonObject parseUrlAsJsonObject(URL url) {
        return new JsonParser().parse(parseUrl(url)).getAsJsonObject();
    }

    public JsonObject parseUrlAsJsonObject(String urlString) {
        return new JsonParser().parse(parseUrl(urlString)).getAsJsonObject();
    }

    private String parseUrl(String urlString) {

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return this.parseUrl(url);
    }

    private String parseUrl(URL url) {
        BufferedReader reader = null;

        try {
            URLConnection connection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
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
}
