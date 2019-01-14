package at.saith.twasi.lot.lol.summoner;


import at.saith.twasi.lot.lol.SummonerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Summoner {

    private SummonerProperties properties;
    private HashMap<QueueType, SummonerRankedStats> rankedStats;
    private Region region;

    public Summoner(SummonerProperties properties,
                    ArrayList<SummonerRankedStats> stats,
                    Region region) {
        this.properties = properties;
        this.rankedStats = new HashMap<>();
        this.region = region;
        setRankedStats(stats);
    }
    /*

    public Summoner(MongoDBSummoner summoner,String region) {
        this(summoner.getProperties(), summoner.getRankedStats(),region);
    }
    public Summoner(MongoDBSummoner.Properties properties, List<RankedStats> rankedStats, String region) {
        this.properties = new SummonerProperties(
                properties.getProfileIconId(),
                properties.getName(), properties.getSummonerLevel(),
                properties.getRevisionDate(),
                properties.getId(),
                properties.getAccountId(),
                properties.getLastUpdate());
        this.rankedStats = new HashMap<>();
        if(rankedStats != null) {
            for(RankedStats rankedStat : rankedStats) {
                this.rankedStats.put(
                        QueueType.byName(rankedStat.getQueueType()),
                        new SummonerRankedStats(rankedStat.getRank(),
                                rankedStat.getTier(),
                                rankedStat.getLeaguePoints(),
                                rankedStat.getMiniSeries(),
                                rankedStat.getQueueType(),
                                rankedStat.getWins(),
                                rankedStat.getLosses()));
            }
        }
        this.region = Region.byName(region);
    }
*/

    public Summoner() {
        this.properties = new SummonerProperties();
        this.rankedStats = new HashMap<>();
        this.region = Region.EUW1;
    }

    public SummonerProperties getProperties() {
        return properties;
    }

    public SummonerRankedStats getRankedStats(QueueType type) {
        if (type == null)
            return null;
        return rankedStats.get(type);
    }

    public Region getRegion() {
        return this.region;
    }

    private void setRankedStats(List<SummonerRankedStats> rankedStats) {
        for (SummonerRankedStats stat : rankedStats) {
            this.rankedStats.put(stat.getQueueType(), stat);
        }
    }

    public static Summoner byId(long id, Region region) {
        return SummonerUtil.getSummoner(id, region);
    }

    public static Summoner byName(String summonerName) {
        return byName(summonerName, Region.EUW1);
    }

    public static Summoner byName(String summonerName, Region server) {
        return SummonerUtil.getSummoner(summonerName, server);

    }
}
