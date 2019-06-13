package at.saith.twasi.lot.api.models;

import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerRankedStats;

import java.util.ArrayList;
import java.util.List;

public class SummonerDTO {

    private final SummonerPropertiesDTO properties;
    private final List<SummonerRankedStatsDTO> rankedStats;
    private final Region region;


    public SummonerDTO(Summoner summoner){
        properties = new SummonerPropertiesDTO(summoner.getProperties());
        rankedStats = new ArrayList<>();
        for(SummonerRankedStats stats:summoner.getRankedStats()){
            rankedStats.add(new SummonerRankedStatsDTO(stats));
        }
        region = summoner.getRegion();
    }
    public SummonerPropertiesDTO getProperties() {
        return properties;
    }

    public List<SummonerRankedStatsDTO> getRankedStats() {
        return rankedStats;
    }

    public Region getRegion() {
        return region;
    }

}
