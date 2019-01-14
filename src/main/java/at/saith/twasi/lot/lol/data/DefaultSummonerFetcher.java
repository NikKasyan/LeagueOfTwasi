package at.saith.twasi.lot.lol.data;

import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerProperties;

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
}
