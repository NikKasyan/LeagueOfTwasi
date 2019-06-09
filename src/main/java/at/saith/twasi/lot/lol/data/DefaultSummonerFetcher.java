package at.saith.twasi.lot.lol.data;

import at.saith.twasi.lot.lol.summoner.*;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultSummonerFetcher extends SummonerFetcher {

    public DefaultSummonerFetcher() {
        super("None");
    }


    public Summoner getSummonerById(String id, Region region) {
        return new Summoner();
    }

    @Override
    public Summoner getSummonerByName(String name, Region region) {

        return getSummonerById("", null);
    }

    @Override
    public SummonerProperties getPropertyByName(String name, Region region) {
        return new SummonerProperties();
    }

    @Override
    public SummonerProperties getPropertyById(String name, Region region) {
        return getPropertyByName("", region);
    }

    @Override
    public ArrayList<SummonerRankedStats> getRankedStats(String id, Region region) {
        return new ArrayList<>(Arrays.asList(new SummonerRankedStats()));
    }

    @Override
    public SummonerRankedStats getRankedStats(String id, Region region, QueueType type) {
        return getRankedStats(id, region).get(0);
    }
}
