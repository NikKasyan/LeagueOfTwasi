package at.saith.twasi.lot.lol.data.database.mongodb;

import at.saith.twasi.lot.lol.data.database.mongodb.summoner.SummonerEntity;
import at.saith.twasi.lot.lol.summoner.Region;
import at.saith.twasi.lot.lol.summoner.Summoner;
import net.twasi.core.database.lib.Repository;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class SummonerRepository extends Repository<SummonerEntity> {

    public Summoner getSummoner(String id) {
        SummonerEntity summoner = getSummonerById(id);
        return summoner.getSummoner();
    }

    public SummonerEntity getSummonerById(String id) {
        Query<SummonerEntity> summonerQuery = store.createQuery(SummonerEntity.class);
        List<SummonerEntity> summonerList = summonerQuery.field("id").equal(id).asList();
        if (summonerList == null || summonerList.size() == 0)
            return new SummonerEntity();
        return summonerList.get(0);
    }

    public Summoner getSummonerByName(String name, Region region) {
        Query<SummonerEntity> summonerQuery = store.createQuery(SummonerEntity.class);
        List<SummonerEntity> summonerList = summonerQuery.field("name").equal(name).field("region").equal(region.name()).asList();
        if (summonerList == null || summonerList.size() == 0)
            return new SummonerEntity().getSummoner();
        return summonerList.get(0).getSummoner();
    }


    public void updateSummoner(Summoner summoner) {
        SummonerEntity summonerEntity = new SummonerEntity(summoner);
        store.save(summonerEntity);
    }


}
