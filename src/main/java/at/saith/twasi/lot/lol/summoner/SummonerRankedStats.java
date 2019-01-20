package at.saith.twasi.lot.lol.summoner;


import com.google.gson.*;

public class SummonerRankedStats {

    private String rank;
    private String tier;
    private long leaguePoints;
    private String miniSeriesProgress;
    private QueueType queueType;
    private long wins;
    private long losses;

    public SummonerRankedStats() {
    }

    public SummonerRankedStats(String json) {
        this(new JsonParser().parse(json).getAsJsonObject());
    }

    public SummonerRankedStats(JsonObject jsonObject) {

        this.rank = jsonObject.get("rank").getAsString();
        this.tier = jsonObject.get("tier").getAsString();
        this.leaguePoints = jsonObject.get("leaguePoints").getAsLong();
        JsonElement miniSeries = jsonObject.get("miniSeries");
        if (miniSeries == null) {
            this.miniSeriesProgress = "";
        } else {
            this.miniSeriesProgress = miniSeries.getAsJsonObject().get("progress").getAsString();
        }

        String queueTypeString = jsonObject.get("queueType").getAsString();
        this.queueType = QueueType.byName(queueTypeString);
        this.wins = jsonObject.get("wins").getAsLong();
        this.losses = jsonObject.get("losses").getAsLong();
    }

    public SummonerRankedStats(String rank, String tier,
                               long lp, String mini,
                               String queueType, long wins, long losses) {
        this.rank = rank;
        this.tier = tier;
        this.leaguePoints = lp;
        this.miniSeriesProgress = mini;
        this.queueType = QueueType.byName(queueType);
        this.wins = wins;
        this.losses = losses;
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

    public boolean hasMiniSeries() {
        return !miniSeriesProgress.equals("");
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
