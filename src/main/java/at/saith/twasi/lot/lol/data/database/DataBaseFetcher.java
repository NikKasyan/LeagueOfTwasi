package at.saith.twasi.lot.lol.data.database;

import at.saith.twasi.lot.lol.RiotUtil;
import at.saith.twasi.lot.lol.data.SummonerFetcher;
import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;

import java.util.ArrayList;

public abstract class DataBaseFetcher extends SummonerFetcher {

    protected ArrayList<Summoner> SUMMONER_CACHE = new ArrayList<>();

    public DataBaseFetcher(String definition) {
        super(definition);
    }

    public abstract Summoner getSummonerFromDatabase(String id, Region region);

    public Summoner loadFromCache(String summonerIdentifier, Region region) {
        for (Summoner summoner : SUMMONER_CACHE) {
            if ((summoner.getProperties().getId().equals(summonerIdentifier)
                    || summoner.getProperties().getName().equalsIgnoreCase(summonerIdentifier)) && region == summoner.getRegion()) {
                if (!RiotUtil.summonerUpToDate(summoner)) {
                    return null;
                }
                SUMMONER_CACHE.remove(summoner);
                return summoner;
            }
        }
        return null;
    }
}
