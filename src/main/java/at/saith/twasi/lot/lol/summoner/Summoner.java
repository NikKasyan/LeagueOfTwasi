package at.saith.twasi.lot.lol.summoner;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    public long getLastUpdate() {
        return lastUpdate;
    }

    public Collection<SummonerRankedStats> getRankedStats() {
        return rankedStats.values();
    }
}
