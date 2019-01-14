package at.saith.twasi.lot.lol.data.database.mongodb;

import at.saith.twasi.lot.LeagueOfTwasi;
import at.saith.twasi.lot.lol.data.RiotUtil;
import at.saith.twasi.lot.lol.data.database.DataBaseFetcher;
import at.saith.twasi.lot.lol.data.database.mongodb.summoner.MongoDBSummoner;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.lot.lol.summoner.*;

import java.util.ArrayList;

public class MongoDBFetcher extends DataBaseFetcher {
    public MongoDBFetcher(String apikey) throws InvalidAPIKeyException {
        super("MongoDB");
        RiotUtil.setup(apikey);
        LeagueOfTwasi.getDataBase().getMorphia().mapPackageFromClass(MongoDBSummoner.Properties.class);
        LeagueOfTwasi.getDataBase().getMorphia().mapPackageFromClass(MongoDBSummoner.RankedStats.class);
    }

    @Override
    public Summoner getSummonerById(String id, Region region) {
        Summoner summoner = loadFromCache(id, region);

        return null;
    }

    @Override
    public Summoner getSummonerByName(String name, Region region) {
        return null;
    }

    @Override
    public SummonerProperties getPropertyByName(String name, Region region) {

        return null;
    }

    @Override
    public SummonerProperties getPropertyById(String name, Region region) {
        return null;
    }

    @Override
    public ArrayList<SummonerRankedStats> getRankedStats(String id, Region region) {
        return null;
    }

    @Override
    public SummonerRankedStats getRankedStats(String id, Region region, QueueType type) {
        return null;
    }

    private Summoner loadFromCache(String summonerIdentifier, Region region) {
        for (Summoner summoner : SUMMONER_CACHE) {
            if ((summoner.getProperties().getId().equals(summonerIdentifier)
                    || summoner.getProperties().getName().equalsIgnoreCase(summonerIdentifier)) && region == summoner.getRegion()) {
                if (true/*Check if uptodate*/) {
                    return null;
                }
                return summoner;
            }
        }
        return null;
    }

}
