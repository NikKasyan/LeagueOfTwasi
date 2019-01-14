package at.saith.twasi.lot.lol.data;

import at.saith.twasi.lot.lol.summoner.*;

import java.util.ArrayList;

public abstract class SummonerFetcher {
    private String description;

    public SummonerFetcher(String description) {
        this.description = description;
    }

    public abstract Summoner getSummonerById(String id, Region region);

    public abstract Summoner getSummonerByName(String name, Region region);

    public abstract SummonerProperties getPropertyByName(String name, Region region);

    public abstract SummonerProperties getPropertyById(String id, Region region);

    public abstract ArrayList<SummonerRankedStats> getRankedStats(String id, Region region);

    public abstract SummonerRankedStats getRankedStats(String id, Region region, QueueType type);
    public final String toString() {
        return description;
    }


}
