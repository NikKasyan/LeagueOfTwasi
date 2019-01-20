package at.saith.twasi.lot.lol;

import at.saith.twasi.lot.lol.data.SummonerFetcher;
import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerProperties;

public class SummonerUtil {
    private static SummonerFetcher datafetcher;

    public static void setup(SummonerFetcher fetcher) {
        if (datafetcher == null) {
            datafetcher = fetcher;
        }
    }

    public static Summoner getSummonerByIdentifier(String identifier, Region region) {
        if (identifier.length() > 16) {
            return getSummonerById(identifier, region);
        } else {
            return getSummonerByName(identifier, region);
        }
    }

    public static SummonerProperties getPropertyByIdentifier(String identifier, Region region) {
        if (identifier.length() > 16) {
            return getPropertyById(identifier, region);
        } else {
            return getPropertyByName(identifier, region);
        }
    }

    public static SummonerProperties getPropertyById(String id, Region region) {
        return datafetcher.getPropertyById(id, region);
    }

    public static SummonerProperties getPropertyByName(String name, Region region) {
        return datafetcher.getPropertyByName(name, region);
    }

    public static Summoner getSummonerById(String id, Region region) {
        return datafetcher.getSummonerById(id, region);
    }

    public static Summoner getSummonerByName(String name, Region region) {
        return datafetcher.getSummonerByName(name, region);
    }
}
