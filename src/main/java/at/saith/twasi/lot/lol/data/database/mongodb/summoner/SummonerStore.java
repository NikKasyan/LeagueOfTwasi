package at.saith.twasi.lot.lol.data.database.mongodb.summoner;

import at.saith.twasi.lot.LeagueOfTwasi;
import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class SummonerStore {

    public static Summoner getSummoner(String id) {
        MongoDBSummoner summoner = getMongoDBSummoner(id);
        return summoner.toSummoner();
    }

    public static MongoDBSummoner getMongoDBSummoner(String id) {
        Query<MongoDBSummoner> summonerQuery = LeagueOfTwasi.getDataBase().getStore().createQuery(MongoDBSummoner.class);
        List<MongoDBSummoner> summonerList = summonerQuery.field("id").equal(id).asList();
        if (summonerList == null || summonerList.size() == 0)
            return new MongoDBSummoner();
        return summonerList.get(0);
    }

    public static MongoDBSummoner getMongoDBSummoner(String name, Region region) {
        Query<MongoDBSummoner> summonerQuery = LeagueOfTwasi.getDataBase().getStore().createQuery(MongoDBSummoner.class);
        List<MongoDBSummoner> summonerList = summonerQuery.field("name").equal(name).field("region").equal(region.name()).asList();
        if (summonerList == null || summonerList.size() == 0)
            return new MongoDBSummoner();
        return summonerList.get(0);
    }

    public static Summoner getSummonerByName(String name, Region region) {
        return getMongoDBSummoner(name, region).toSummoner();
    }

    public static void updateSummoner(Summoner summoner) {
        MongoDBSummoner mongoDBSummoner = MongoDBSummoner.fromSummoner(summoner);
        LeagueOfTwasi.getDataBase().getStore().save(mongoDBSummoner);
    }


}
