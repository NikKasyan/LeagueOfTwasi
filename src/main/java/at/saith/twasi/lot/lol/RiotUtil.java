package at.saith.twasi.lot.lol;

import at.saith.twasi.lot.json.JsonUrlParser;
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
import java.util.HashMap;

public class RiotUtil {
    //private static String API_KEY;
    private static HashMap<Region, RateLimitationManager> rateLimitationManagers;
    private static final long updateRate = 120_000L;//In millis
    private static final String BASE_URL = "https://%REGION%.api.riotgames.com/";
    private static final String STATUS_URL = BASE_URL + "lol/status/v3/shard-data";
    private static final String SUMMONER_BY_NAME_URL = BASE_URL + "lol/summoner/v4/summoners/by-name/";
    private static final String SUMMONER_BY_ID_URL = BASE_URL + "lol/summoner/v4/summoners/";
    private static final String RANKSTATS_BY_ID_URL = BASE_URL + "lol/league/v4/positions/by-summoner/";

    //TODO: ADD Rate-Limitations
    // https://developer.riotgames.com/rate-limiting.html


    public static Summoner getSummonerByProperty(SummonerProperties properties, Region region, String apikey) {
        ArrayList<SummonerRankedStats> stats = getRankedStatsById(properties.getId(), region, apikey);
        return new Summoner(properties, stats, region);
    }

    public static Summoner getSummonerByName(String name, Region region, String apikey) {
        SummonerProperties properties = getPropertyByName(name, region, apikey);
        ArrayList<SummonerRankedStats> rankedStats = getRankedStatsById(properties.getId(), region, apikey);
        return new Summoner(properties, rankedStats, region);
    }

    public static Summoner getSummonerById(String id, Region region, String apikey) {
        SummonerProperties properties = getPropertyById(id, region, apikey);
        ArrayList<SummonerRankedStats> rankedStats = getRankedStatsById(id, region, apikey);
        return new Summoner(properties, rankedStats, region);
    }

    public static SummonerProperties getPropertyByIdentifier(String identifier, Region region, String apikey) {
        if (identifier.length() > 16) {
            return getPropertyById(identifier, region, apikey);
        } else {
            return getPropertyByName(identifier, region, apikey);
        }
    }

    public static SummonerProperties getPropertyById(String id, Region region, String apikey) {
        JsonObject obj = getJsonObjectFromUrl(SUMMONER_BY_ID_URL, region, id, apikey);
        if (obj == null) return null;
        return new SummonerProperties(obj);
    }

    public static SummonerProperties getPropertyByName(String name, Region region, String apikey) {
        JsonObject obj = getJsonObjectFromUrl(SUMMONER_BY_NAME_URL, region, name, apikey);
        if (obj == null) return null;
        return new SummonerProperties(obj);
    }

    public static ArrayList<SummonerRankedStats> getRankedStatsById(String id, Region region, String apikey) {
        ArrayList<SummonerRankedStats> stats = new ArrayList<>();
        JsonArray array = getJsonArrayFromUrl(RANKSTATS_BY_ID_URL, region, id, apikey);
        for (JsonElement element : array) {
            if (element instanceof JsonObject) {
                stats.add(new SummonerRankedStats(element.getAsJsonObject()));
            }
        }
        return stats;
    }

    public static boolean summonerUpToDate(Summoner summoner, String apikey) {
        return summoner.getLastUpdate() + updateRate >= System.currentTimeMillis();
    }

    private static JsonArray getJsonArrayFromUrl(String urlString, Region region, String param, String apikey) {
        URL summonerPropertiesUrl = getValidURL(urlString, param, region, apikey);
        if (checkRateLimitation(region, urlString)) {

        }
        return new JsonUrlParser(summonerPropertiesUrl).parseUrlAsJsonElement().getAsJsonArray();
    }

    private static boolean checkRateLimitation(Region region, String urlString) {
        return true;
        /*URL url = getValidURL(urlString,region);
        RateLimitationManager rateLimitationManager = rateLimitationManagers.get(region);
        if(rateLimitationManager == null){
            rateLimitationManager = new RateLimitationManager();
            rateLimitationManagers.put(region,rateLimitationManager);
        }
        return rateLimitationManager.check(url);
    */
    }

    private static JsonObject getJsonObjectFromUrl(String urlString, Region region, String param, String apikey) {
        URL summonerPropertiesUrl = getValidURL(urlString, param, region, apikey);

        return getJsonObjectFromUrl(summonerPropertiesUrl);
    }

    private static JsonObject getJsonObjectFromUrl(URL url) {

        return new JsonUrlParser(url).parseUrlAsJsonObject();
    }

    public static boolean isValidKey(String apikey) {
        URL statusUrl = getValidURL(STATUS_URL, "euw1", apikey);
        JsonObject object = getJsonObjectFromUrl(statusUrl);
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


    private static URL getValidURL(String urlString, String param, Region region, String apikey) {
        return getValidURL(urlString + param, region.toString(), apikey);
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

    private static URL getValidURL(String urlString, Region region, String apikey) {
        return getValidURL(urlString, region.name(), apikey);
    }

}
