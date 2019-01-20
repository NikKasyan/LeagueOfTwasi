package at.saith.twasi.lot.lol.data.database.mongodb;

import at.saith.twasi.lot.LeagueOfTwasi;
import at.saith.twasi.lot.lol.RiotUtil;
import at.saith.twasi.lot.lol.data.database.DataBaseFetcher;
import at.saith.twasi.lot.lol.data.database.mongodb.summoner.MongoDBSummoner;
import at.saith.twasi.lot.lol.data.database.mongodb.summoner.SummonerStore;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.lot.lol.summoner.*;

import java.util.ArrayList;

public class MongoDBFetcher extends DataBaseFetcher {
    public MongoDBFetcher(String apikey) throws InvalidAPIKeyException {
        super("MongoDB");
        RiotUtil.setup(apikey);
        LeagueOfTwasi.getDataBase().getMorphia().mapPackageFromClass(MongoDBSummoner.class);
    }

    @Override
    public Summoner getSummonerById(String id, Region region) {
        Summoner summoner = loadFromCache(id, region);
        SummonerProperties properties = null;
        try {
            properties = RiotUtil.getPropertyByIdentifier(id, region);
        } catch (Exception e) {
            return SummonerStore.getSummoner(id);
        }
        if (summoner != null) {
            if (!RiotUtil.summonerUpToDate(summoner)
                    && summoner.getProperties().getRevisionDate() < properties.getRevisionDate()) {
                summoner = RiotUtil.getSummonerByProperty(properties, region);
            }
        } else {
            summoner = RiotUtil.getSummonerByProperty(properties, region);
        }
        SummonerStore.updateSummoner(summoner);
        SUMMONER_CACHE.add(summoner);
        return summoner;
    }

    @Override
    public Summoner getSummonerByName(String name, Region region) {
        Summoner summoner = loadFromCache(name, region);
        SummonerProperties properties = null;
        try {
            properties = RiotUtil.getPropertyByIdentifier(name, region);
        } catch (Exception e) {
            return SummonerStore.getSummonerByName(name, region);
        }
        if (summoner != null) {
            if (!RiotUtil.summonerUpToDate(summoner)
                    && summoner.getProperties().getRevisionDate() < properties.getRevisionDate()) {
                summoner = RiotUtil.getSummonerByProperty(properties, region);
            }
        } else {
            summoner = RiotUtil.getSummonerByProperty(properties, region);
        }
        SummonerStore.updateSummoner(summoner);
        SUMMONER_CACHE.add(summoner);
        return summoner;
    }

    @Override
    public SummonerProperties getPropertyByName(String name, Region region) {

        return RiotUtil.getPropertyByName(name, region);
    }

    @Override
    public SummonerProperties getPropertyById(String id, Region region) {
        return RiotUtil.getPropertyById(id, region);
    }

    @Override
    public ArrayList<SummonerRankedStats> getRankedStats(String id, Region region) {
        return RiotUtil.getRankedStatsById(id, region);
    }

    @Override
    public SummonerRankedStats getRankedStats(String id, Region region, QueueType type) {
        throw new UnsupportedOperationException("getRankedStats(String id, Region region, QueueType type) not implemented.");
    }

    private Summoner loadFromCache(String summonerIdentifier, Region region) {
        for (Summoner summoner : SUMMONER_CACHE) {
            if ((summoner.getProperties().getId().equals(summonerIdentifier)
                    || summoner.getProperties().getName().equalsIgnoreCase(summonerIdentifier)) && region == summoner.getRegion()) {
                if (!RiotUtil.summonerUpToDate(summoner)) {
                    System.out.println("test");
                    return null;
                }
                SUMMONER_CACHE.remove(summoner);
                return summoner;
            }
        }
        return null;
    }

}
