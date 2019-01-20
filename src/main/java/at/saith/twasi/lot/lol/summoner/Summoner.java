package at.saith.twasi.lot.lol.summoner;


import at.saith.twasi.lot.lol.SummonerUtil;
import at.saith.twasi.lot.lol.data.database.mongodb.summoner.MongoDBSummoner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Summoner {

    private SummonerProperties properties;
    private HashMap<QueueType, SummonerRankedStats> rankedStats;
    private Region region;
    private long lastUpdate;

    public Summoner(SummonerProperties properties,
                    ArrayList<SummonerRankedStats> stats,
                    Region region) {
        this.properties = properties;
        this.rankedStats = new HashMap<>();
        this.region = region;
        this.lastUpdate = System.currentTimeMillis();
        setRankedStats(stats);
    }


    public Summoner(MongoDBSummoner summoner, String region) {
        this(summoner.getProperties(), summoner.getRankedStats(), region);
    }

    public Summoner(MongoDBSummoner.Properties properties, List<MongoDBSummoner.RankedStats> rankedStats, String region) {
        this.properties = new SummonerProperties(properties.getProfileIconId(),
                properties.getName(), properties.getSummonerLevel(),
                properties.getRevisionDate(), properties.getId(),
                properties.getAccountId(), properties.getPuuid());
        this.rankedStats = new HashMap<>();
        if (rankedStats != null) {
            for (MongoDBSummoner.RankedStats rankedStat : rankedStats) {
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

    public static Summoner byId(String id, Region region) {
        return SummonerUtil.getSummonerById(id, region);
    }

    public static Summoner byName(String summonerName) {
        return byName(summonerName, Region.EUW1);
    }

    public static Summoner byName(String summonerName, Region region) {
        return SummonerUtil.getSummonerByName(summonerName, region);

    }

    public String toString() {
        StringBuilder rankedStatsString = new StringBuilder("");
        for (SummonerRankedStats rankedStat : rankedStats.values()) {
            rankedStatsString.append("," + rankedStat);
        }
        return "{\n" +
                "\"properties\":" + properties + ",\n" +
                "\"stats\":[\n" + rankedStatsString.substring(1) + "\n], \n" +
                "\"lastupdate\": " + lastUpdate + ",\n" +
                "\"region\":\"" + region + "\"" +
                "\n}";
    }
    public long getLastUpdate() {
        return lastUpdate;
    }

    public Collection<SummonerRankedStats> getRankedStats() {
        return rankedStats.values();
    }
}
