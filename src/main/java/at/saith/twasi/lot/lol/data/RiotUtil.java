package at.saith.twasi.lot.lol.data;

import at.saith.twasi.lot.json.JsonUrlParser;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.lot.lol.summoner.Region;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.twasi.core.logger.TwasiLogger;

import java.net.MalformedURLException;
import java.net.URL;

public class RiotUtil {
    private static String API_KEY;
    private static final String BASE_URL = "https://%REGION%.api.riotgames.com/";
    private static final String STATUS_URL = BASE_URL + "lol/status/v3/shard-data";

    public static void setup(String apikey) throws InvalidAPIKeyException {
        if (API_KEY == null) {
            API_KEY = apikey;
            if (!isValidKey(apikey)) {
                API_KEY = null;
                throw new InvalidAPIKeyException(apikey);
            }
        }
    }

    private static boolean isValidKey(String apikey) {
        JsonObject object = new JsonUrlParser().parseUrlAsJsonObject(getValidURL(STATUS_URL, "euw1", apikey));
        if (object == null) {
            return false;
        }
        JsonElement status = object.get("status");
        if (status == null) {
            return true;
        }
        TwasiLogger.log.error(status.getAsJsonObject().get("message"));
        return false;
    }

    private static URL getValidURL(String urlString, String method, String param, Region region) {
        return getValidURL(urlString + method + param, region.toString(), API_KEY);
    }

    private static URL getValidURL(String urlString, String regionString, String apiKey) {
        URL url = null;
        try {
            url = new URL(urlString.replace("%REGION%", regionString.toLowerCase()) + "?api_key=" + apiKey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static URL getValidURL(String urlString, Region region) {
        return getValidURL(urlString, region.name());
    }

    private static URL getValidURL(String urlString, String regionString) {
        return getValidURL(urlString, regionString, API_KEY);
    }
}
