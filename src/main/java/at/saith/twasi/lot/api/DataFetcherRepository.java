package at.saith.twasi.lot.api;

import net.twasi.core.database.lib.Repository;
import net.twasi.core.database.models.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.InsertOptions;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class DataFetcherRepository extends Repository<DataFetcherEntity> {
    public void updateApiKey(User user, String apikey) {
            store.save(new DataFetcherEntity(user.getId(),apikey));
    }
    public String getApikey(User user){
            Query<DataFetcherEntity> query = store.createQuery(DataFetcherEntity.class);
            List<DataFetcherEntity> fetcher = query.
                    field("id").
                    equal(user.getId()).
                    asList();
            if (fetcher == null || fetcher.size() == 0)
                return "";
            return fetcher.get(0).getApikey();

    }
}
