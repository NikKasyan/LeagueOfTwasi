package at.saith.twasi.lot.api.models;

import at.saith.twasi.lot.lol.summoner.SummonerProperties;

public class SummonerPropertiesDTO {
    private final long profileIconId;
    private final String name;
    private final long summonerLevel;
    private final long revisionDate;
    private final String id;
    private final String accountId;
    private final String puuid;
    public SummonerPropertiesDTO(SummonerProperties properties) {
        this.profileIconId = properties.getProfileIconId();
        this.name = properties.getName();
        this.summonerLevel = properties.getSummonerLevel();
        this.revisionDate = properties.getRevisionDate();
        this.id = properties.getId();
        this.accountId = properties.getAccountId();
        this.puuid = properties.getPuuid();
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
