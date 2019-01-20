package at.saith.twasi.lot.lol.data.database.mongodb.summoner;

import at.saith.twasi.lot.lol.summoner.Summoner;
import at.saith.twasi.lot.lol.summoner.SummonerProperties;
import at.saith.twasi.lot.lol.summoner.SummonerRankedStats;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.NotSaved;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(value = "Summoner", noClassnameStored = true)
public class MongoDBSummoner {

    private Properties properties;
    private List<RankedStats> rankedstats;
    private String region;
    @Id
    private String id;
    private String name;
    @NotSaved
    private boolean saveable = true;

    public MongoDBSummoner() {
    }

    public boolean isSaveable() {
        return saveable;
    }


    public MongoDBSummoner(Properties properties, List<RankedStats> stats, String regionString) {
        this.properties = properties;
        this.id = properties.getId();
        this.name = properties.getName();
        this.rankedstats = stats;
        this.region = regionString;
        this.saveable = properties.isSaveable() && stats.get(0).isSaveable();
    }

    public Summoner toSummoner() {
        return new Summoner(properties, rankedstats, region);
    }

    public static MongoDBSummoner fromSummoner(Summoner summoner) {

        SummonerProperties summonerProperties = summoner.getProperties();
        Collection<SummonerRankedStats> rankedStats = summoner.getRankedStats();
        Properties properties = new Properties();
        List<RankedStats> stats = new ArrayList<>(rankedStats.size());

        properties.
                setRevisionDate(summonerProperties.getRevisionDate()).
                setProfileIconId(summonerProperties.getProfileIconId()).
                setPuuid(summonerProperties.getPuuid()).
                setAccountId(summonerProperties.getAccountId()).
                setSummonerLevel(summonerProperties.getSummonerLevel()).
                setName(summonerProperties.getName()).
                setId(summonerProperties.getId());
        for (SummonerRankedStats rankedStat : rankedStats) {
            RankedStats stat = new RankedStats();
            stat.
                    setQueueType(rankedStat.getQueueType().name()).
                    setLeaguePoints(rankedStat.getLeaguePoints()).
                    setLosses(rankedStat.getLosses()).
                    setWins(rankedStat.getWins()).
                    setMiniSeries(rankedStat.getMiniSeriesProgress()).
                    setRank(rankedStat.getRank()).
                    setTier(rankedStat.getTier());
            stats.add(stat);
        }


        return new MongoDBSummoner(properties, stats, summoner.getRegion().name());
    }

    public Properties getProperties() {
        return properties;
    }

    public List<RankedStats> getRankedStats() {
        return rankedstats;
    }


    public String getRegion() {
        return region;
    }

    public String getId() {
        return id;
    }


    public static class Properties {

        private long profileIconId;
        private String name;
        private long summonerLevel;
        private long revisionDate;
        private String id;
        private String accountId;
        private String puuid;
        @NotSaved
        private boolean saveable = true;

        public boolean isSaveable() {
            return saveable;
        }


        public Properties() {
        }

        public Properties(boolean saveable) {
            this.saveable = saveable;
        }

        public Properties setProfileIconId(long profileIconId) {
            this.profileIconId = profileIconId;
            return this;
        }

        public Properties setName(String name) {
            this.name = name;
            return this;
        }

        public Properties setSummonerLevel(long summonerLevel) {
            this.summonerLevel = summonerLevel;
            return this;
        }

        public Properties setRevisionDate(long revisionDate) {
            this.revisionDate = revisionDate;
            return this;
        }

        public Properties setId(String id) {
            this.id = id;
            return this;
        }

        public Properties setAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Properties setPuuid(String puuid) {
            this.puuid = this.puuid;
            return this;
        }

        public long getProfileIconId() {
            return profileIconId;
        }

        public String getName() {
            return name;
        }

        public long getSummonerLevel() {
            return summonerLevel;
        }

        public long getRevisionDate() {
            return revisionDate;
        }

        public String getId() {
            return id;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getPuuid() {
            return puuid;
        }

    }

    public static class RankedStats {
        private String rank;
        private String tier;
        private long leaguePoints;
        private String miniSeries;//progress
        private String queueType;
        private long wins;
        private long losses;
        @NotSaved
        private boolean saveable = true;

        public RankedStats() {
        }

        public RankedStats(boolean saveable) {
            this.saveable = saveable;
        }

        public boolean isSaveable() {
            return saveable;
        }
        public String getRank() {
            return rank;
        }

        public RankedStats setRank(String rank) {
            this.rank = rank;
            return this;
        }

        public String getTier() {
            return tier;
        }

        public RankedStats setTier(String tier) {
            this.tier = tier;
            return this;
        }

        public long getLeaguePoints() {
            return leaguePoints;
        }

        public RankedStats setLeaguePoints(long leaguePoints) {
            this.leaguePoints = leaguePoints;
            return this;
        }

        public String getMiniSeries() {
            return miniSeries;
        }

        public RankedStats setMiniSeries(String miniSeries) {
            this.miniSeries = miniSeries;
            return this;
        }

        public String getQueueType() {
            return queueType;
        }

        public RankedStats setQueueType(String queueType) {
            this.queueType = queueType;
            return this;
        }

        public long getWins() {
            return wins;
        }

        public RankedStats setWins(long wins) {
            this.wins = wins;
            return this;
        }

        public long getLosses() {
            return losses;
        }

        public RankedStats setLosses(long losses) {
            this.losses = losses;
            return this;
        }
    }

}