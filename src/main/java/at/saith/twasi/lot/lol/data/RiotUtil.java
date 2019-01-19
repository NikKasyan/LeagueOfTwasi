package at.saith.twasi.lot.lol.data;

import at.saith.twasi.lot.json.JsonUrlParser;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerProperties;
import at.saith.twasi.lot.lol.summoner.SummonerRankedStats;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.twasi.core.logger.TwasiLogger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RiotUtil {
    private static String API_KEY;
    private static RateLimitation rateLimitation;
    private static final String BASE_URL = "https://%REGION%.api.riotgames.com/";
    private static final String STATUS_URL = BASE_URL + "lol/status/v3/shard-data";
    private static final String SUMMONER_BY_NAME_URL = BASE_URL + "lol/summoner/v4/summoners/by-name/";
    private static final String SUMMONER_BY_ID_URL = BASE_URL + "lol/summoner/v4/summoners/";
    private static final String RANKSTATS_BY_ID_URL = BASE_URL + "lol/league/v4/positions/by-summoner/";

    //TODO: ADD Rate-Limitations
    // https://developer.riotgames.com/rate-limiting.html
    public static void setup(String apikey) throws InvalidAPIKeyException {
        if (API_KEY == null) {
            API_KEY = apikey;
            if (!isValidKey(apikey)) {
                API_KEY = null;
                throw new InvalidAPIKeyException(apikey);
            }
            rateLimitation = new RateLimitation();
        }
    }

    public static Summoner getSummonerByName(String name, Region region) {
        SummonerProperties properties = getPropertyByName(name, region);
        ArrayList<SummonerRankedStats> rankedStats = getRankedStatsById(properties.getId(), region);
        return new Summoner(properties, rankedStats, region);
    }

    public static Summoner getSummonerById(String id, Region region) {
        SummonerProperties properties = getPropertyById(id, region);
        ArrayList<SummonerRankedStats> rankedStats = getRankedStatsById(id, region);
        return new Summoner(properties, rankedStats, region);
    }

    public static SummonerProperties getPropertyByIdentifier(String identifier, Region region) {
        if (identifier.length() > 16) {
            return getPropertyById(identifier, region);
        } else {
            return getPropertyByName(identifier, region);
        }
    }

    public static SummonerProperties getPropertyById(String id, Region region) {
        JsonObject obj = getJsonObjectFromUrl(SUMMONER_BY_ID_URL, region, id);
        if (obj == null) return null;
        return new SummonerProperties(obj);
    }

    public static SummonerProperties getPropertyByName(String name, Region region) {
        JsonObject obj = getJsonObjectFromUrl(SUMMONER_BY_NAME_URL, region, name);
        if (obj == null) return null;
        return new SummonerProperties(obj);
    }

    public static ArrayList<SummonerRankedStats> getRankedStatsById(String id, Region region) {
        ArrayList<SummonerRankedStats> stats = new ArrayList<>();
        JsonArray array = getJsonArrayFromUrl(RANKSTATS_BY_ID_URL, region, id);
        for (JsonElement element : array) {
            if (element instanceof JsonObject) {
                stats.add(new SummonerRankedStats(element.getAsJsonObject()));
            }
        }
        return stats;
    }

    private static JsonArray getJsonArrayFromUrl(String urlString, Region region, String params) {
        URL summonerPropertiesUrl = getValidURL(urlString, region);
        return new JsonUrlParser().parseUrlAsJsonElement(summonerPropertiesUrl).getAsJsonArray();
    }

    private static JsonObject getJsonObjectFromUrl(String urlString, Region region, String params) {
        URL summonerPropertiesUrl = getValidURL(urlString, region);
        return new JsonUrlParser().parseUrlAsJsonObject(summonerPropertiesUrl);
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

    private static class RateLimitation {
        public RateLimitation() {

        }
    }
}
