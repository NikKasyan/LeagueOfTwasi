package at.saith.twasi.lot.lol.data.database.mongodb;

import at.saith.twasi.lot.lol.RiotUtil;
import at.saith.twasi.lot.lol.data.database.DataBaseFetcher;
import at.saith.twasi.lot.lol.summoner.*;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;

import java.util.ArrayList;

public class MongoDBFetcher extends DataBaseFetcher {

    private SummonerRepository repository = ServiceRegistry.get(DataService.class).get(SummonerRepository.class);
    private String apikey;
    private boolean valid_key;
    public MongoDBFetcher(String apikey){
        super("MongoDB");
        setAPIKey(apikey);
    }
    public MongoDBFetcher(){
        super("MongoDB");
    }


    @Override
    public Summoner getSummonerById(String id, Region region) {
        if(!valid_key)return null;
        Summoner summoner = loadFromCache(id, region);
        SummonerProperties properties;
        try {
            properties = RiotUtil.getPropertyByIdentifier(id, region, apikey);
        } catch (Exception e) {
            return repository.getSummoner(id);
        }
        return updateSummoner(summoner, properties, region);
    }

    @Override
    public Summoner getSummonerByName(String name, Region region) {
        if(!valid_key)return null;
        Summoner summoner = loadFromCache(name, region);
        SummonerProperties properties;
        //Try Catch if ratelimit exceeded or any other error happend
        try {
            properties = RiotUtil.getPropertyByIdentifier(name, region, apikey);
        } catch (Exception e) {
            return repository.getSummonerByName(name, region);
        }
        return updateSummoner(summoner, properties, region);
    }

    /**
     * Updates the Summoner if he is older than 2 minutes and if his revision date changed.
     * The revision date indicates when a change was made e.g. a game was played, icon was changed, etc.
     * Also adds the summoner to the cache and updates it's entry in the database.
     *
     * @param summoner   Summoner to be updated
     * @param properties New properties for the summoner
     * @param region     region of the summoner
     * @return the updated Summoner
     */
    private Summoner updateSummoner(Summoner summoner, SummonerProperties properties, Region region) {
        if(!valid_key)return null;
        if (summoner != null) {
            if (!RiotUtil.summonerUpToDate(summoner, apikey)
                    && summoner.getProperties().getRevisionDate() < properties.getRevisionDate()) {
                summoner = RiotUtil.getSummonerByProperty(properties, region, apikey);
            }
        } else {
            summoner = RiotUtil.getSummonerByProperty(properties, region, apikey);
        }
        repository.updateSummoner(summoner);
        SUMMONER_CACHE.add(summoner);
        return summoner;
    }

    /**
     * Sets the apikey for this fetcher if it is valid.
     * @param apikey
     * @return if the given apikey was valid
     */
    public boolean setAPIKey(String apikey){
        boolean valid_key = RiotUtil.isValidKey(apikey);
        if(valid_key) {
            this.apikey = apikey;
            this.valid_key = true;
        }
        return valid_key;
    }
    @Override
    public SummonerProperties getPropertyByName(String name, Region region) {
        if(!valid_key)return null;
        return RiotUtil.getPropertyByName(name, region, apikey);
    }

    @Override
    public SummonerProperties getPropertyById(String id, Region region) {
        if(!valid_key)return null;
        return RiotUtil.getPropertyById(id, region, apikey);
    }

    @Override
    public ArrayList<SummonerRankedStats> getRankedStats(String id, Region region) {
        if(!valid_key)return null;
        return RiotUtil.getRankedStatsById(id, region, apikey);
    }

    @Override
    public SummonerRankedStats getRankedStats(String id, Region region, QueueType type) {
        throw new UnsupportedOperationException("getRankedStats(String id, Region region, QueueType type) not implemented.");
    }

    @Override
    public Summoner getSummonerFromDatabase(String id, Region region) {
        if(!valid_key)return null;
        return repository.getSummoner(id);
    }
    public boolean hasValidKey(){
        return valid_key;
    }

    public Summoner getSummonerByIdentifier(String identifier, Region region) {
        if (identifier.length() > 16) {
            return getSummonerById(identifier, region);
        } else {
            return getSummonerByName(identifier, region);
        }
    }
    public SummonerProperties getPropertyByIdentifier(String identifier, Region region) {
        if (identifier.length() > 16) {
            return getPropertyById(identifier, region);
        } else {
            return getPropertyByName(identifier, region);
        }
    }

    /**
     * Returns a Summoner if it was found in the Cache. Might return null if the Summoner wasn't found
     * or isn't up to date(older than 2 Minutes).
     * @param summonerIdentifier an Identifier for a Summoner which can be either a Name or an SummonerId
     * @param region the region in which the Summoner plays
     * @return the Summoner if found in the Cache or null if the summoner wasn't or isn't up to date
     * @see Region
     */
    public Summoner loadFromCache(String summonerIdentifier, Region region) {
        if(!valid_key)return null;
        for (Summoner summoner : SUMMONER_CACHE) {
            if ((summoner.getProperties().getId().equals(summonerIdentifier)
                    || summoner.getProperties().getName().equalsIgnoreCase(summonerIdentifier))
                    && region == summoner.getRegion()) {
                if (!RiotUtil.summonerUpToDate(summoner,apikey)) {
                    return null;
                }
                SUMMONER_CACHE.remove(summoner);
                return summoner;
            }
        }
        return null;
    }
}
