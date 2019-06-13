package at.saith.twasi.lot.api;

import net.twasi.core.database.lib.Repository;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class DataFetcherRepository extends Repository<DataFetcherEntity> {
    public void updateApiKey(String name, String apikey) {
        DataFetcherEntity dataFetcherEntity = new DataFetcherEntity(name, apikey);
        store.save(dataFetcherEntity);
    }
    public String getApikey(String name){

            Query<DataFetcherEntity> fetcherEntity = store.createQuery(DataFetcherEntity.class);
            List<DataFetcherEntity> fetcher = fetcherEntity.field("id").equal(name).asList();
            if (fetcher == null || fetcher.size() == 0)
                return "";
            return fetcher.get(0).getApikey();

    }
}
