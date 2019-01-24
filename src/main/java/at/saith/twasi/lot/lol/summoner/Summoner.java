package at.saith.twasi.lot.lol.summoner;


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
