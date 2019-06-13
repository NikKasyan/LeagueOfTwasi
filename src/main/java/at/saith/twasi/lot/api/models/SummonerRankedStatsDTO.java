package at.saith.twasi.lot.api.models;

import at.saith.twasi.lot.lol.summoner.QueueType;
import at.saith.twasi.lot.lol.summoner.SummonerRankedStats;

public class SummonerRankedStatsDTO {
    private String rank;
    private String tier;
    private long leaguePoints;
    private String miniSeriesProgress;
    private QueueType queueType;
    private long wins;
    private long losses;

    public SummonerRankedStatsDTO(SummonerRankedStats stats) {
        this.rank = stats.getRank();
        this.tier = stats.getTier();
        this.leaguePoints = stats.getLeaguePoints();
        this.miniSeriesProgress = stats.getMiniSeriesProgress();
        this.queueType = stats.getQueueType();
        this.wins = stats.getWins();
        this.losses = stats.getLosses();
    }

    public String getRank() {
        return rank;
    }

    public String getTier() {
        return tier;
    }

    public long getLeaguePoints() {
        return leaguePoints;
    }

    public String getMiniSeriesProgress() {
        return miniSeriesProgress;
    }

    public QueueType getQueueType() {
        return queueType;
    }

    public long getWins() {
        return wins;
    }

    public long getLosses() {
        return losses;
    }
}
