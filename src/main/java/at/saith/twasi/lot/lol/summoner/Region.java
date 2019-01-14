package at.saith.twasi.lot.lol.summoner;

public enum Region {
    RU,
    KR,
    EUW1,
    BR1,
    OC1,
    JP1,
    EUN1,
    TR1,
    LA1,
    LA2;

    public static boolean isRegion(String name) {
        return byName(name) != null;
    }

    public static Region byName(String name) {
        for (Region region : values()) {
            if (region.name().equalsIgnoreCase(name)) {
                return region;
            }
        }
        return null;
    }
}
