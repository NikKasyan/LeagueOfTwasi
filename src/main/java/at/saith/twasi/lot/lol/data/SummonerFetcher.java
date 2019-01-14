package at.saith.twasi.lot.lol.data;

import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerProperties;

public abstract class SummonerFetcher {
    private String description;

    public SummonerFetcher(String description) {
        this.description = description;
    }

    public abstract Summoner getSummonerById(String id, Region region);

    public abstract Summoner getSummonerByName(String name, Region region);

    public abstract SummonerProperties getPropertyByName(String name, Region region);

    public abstract SummonerProperties getPropertyById(String name, Region region);

    public final String toString() {
        return description;
    }


}
