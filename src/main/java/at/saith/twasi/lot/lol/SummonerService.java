package at.saith.twasi.lot.lol;

import at.saith.twasi.lot.lol.data.SummonerFetcher;
import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerProperties;
import net.twasi.core.services.IService;

public class SummonerService implements IService {
    private SummonerFetcher datafetcher;

    public SummonerService(SummonerFetcher fetcher) {
        this.datafetcher = fetcher;
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

    public SummonerProperties getPropertyById(String id, Region region) {
        return datafetcher.getPropertyById(id, region);
    }

    public SummonerProperties getPropertyByName(String name, Region region) {
        return datafetcher.getPropertyByName(name, region);
    }

    public Summoner getSummonerById(String id, Region region) {
        return datafetcher.getSummonerById(id, region);
    }

    public Summoner getSummonerByName(String name, Region region) {
        return datafetcher.getSummonerByName(name, region);
    }
}
