package at.saith.twasi.lot.lol.data.database.mongodb;

import at.saith.twasi.lot.lol.RiotUtil;
import at.saith.twasi.lot.lol.data.database.DataBaseFetcher;
import at.saith.twasi.lot.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.lot.lol.summoner.*;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;

import java.util.ArrayList;

public class MongoDBFetcher extends DataBaseFetcher {

    private  SummonerRepository repository = ServiceRegistry.get(DataService.class).get(SummonerRepository.class);

    public MongoDBFetcher(String apikey) throws InvalidAPIKeyException {
        super("MongoDB");
        RiotUtil.setup(apikey);
    }


    @Override
    public Summoner getSummonerById(String id, Region region) {
        Summoner summoner = loadFromCache(id, region);
        SummonerProperties properties;
        try {
            properties = RiotUtil.getPropertyByIdentifier(id, region);
        } catch (Exception e) {
            return repository.getSummoner(id);
        }
        return updateSummoner(summoner, properties,region);
    }

    @Override
    public Summoner getSummonerByName(String name, Region region) {
        Summoner summoner = loadFromCache(name, region);
        SummonerProperties properties;
        //Try Catch if ratelimit exceeded or any other error happend
        try {
            properties = RiotUtil.getPropertyByIdentifier(name, region);
        } catch (Exception e) {
            return repository.getSummonerByName(name, region);
        }
        return updateSummoner(summoner,properties,region);
    }

    /**
     * Updates the Summoner if he is older than 2 minutes and if his revision date changed.
     * The revision date indicates when a change was made e.g. a game was played, icon was changed, etc.
     * Also adds the summoner to the cache and updates it's entry in the database.
     * @param summoner Summoner to be updated
     * @param properties New properties for the summoner
     * @param region region of the summoner
     * @return the updated Summoner
     */
    private Summoner updateSummoner(Summoner summoner, SummonerProperties properties, Region region){

        if (summoner != null) {
            if (!RiotUtil.summonerUpToDate(summoner)
                    && summoner.getProperties().getRevisionDate() < properties.getRevisionDate()) {
                summoner = RiotUtil.getSummonerByProperty(properties, region);
            }
        } else {
            summoner = RiotUtil.getSummonerByProperty(properties, region);
        }
        repository.updateSummoner(summoner);
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

    @Override
    public Summoner getSummonerFromDatabase(String id, Region region) {
        return repository.getSummoner(id);
    }

}
