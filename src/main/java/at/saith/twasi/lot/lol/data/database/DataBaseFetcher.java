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


}
