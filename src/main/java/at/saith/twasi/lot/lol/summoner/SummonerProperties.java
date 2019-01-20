package at.saith.twasi.lot.lol.summoner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SummonerProperties {

    private long profileIconId;
    private String name;
    private long summonerLevel;
    private long revisionDate;
    private String id;
    private String accountId;
    private String puuid;

    public SummonerProperties() {
    }

    public SummonerProperties(String json) {
        this(new JsonParser().parse(json).getAsJsonObject());

    }

    public SummonerProperties(JsonObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        this.profileIconId = jsonObject.get("profileIconId").getAsLong();
        this.name = jsonObject.get("name").getAsString();
        this.summonerLevel = jsonObject.get("summonerLevel").getAsLong();
        this.revisionDate = jsonObject.get("revisionDate").getAsLong();
        this.id = jsonObject.get("id").getAsString();
        this.accountId = jsonObject.get("accountId").getAsString();
        this.puuid = jsonObject.get("puuid").getAsString();
    }

    public SummonerProperties(long profileIconId, String name,
                              long summonerLevel,
                              long revisionDate, String id, String accountId,
                              String puuid) {
        this.profileIconId = profileIconId;
        this.name = name;
        this.summonerLevel = summonerLevel;
        this.id = id;
        this.puuid = puuid;
        this.revisionDate = revisionDate;
        this.accountId = accountId;
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

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
