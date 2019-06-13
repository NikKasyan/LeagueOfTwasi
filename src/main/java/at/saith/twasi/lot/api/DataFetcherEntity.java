package at.saith.twasi.lot.api;

import net.twasi.core.database.models.BaseEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "datafetcher", noClassnameStored = true)
public class DataFetcherEntity extends BaseEntity {
    @Id
    ObjectId id;
    private String apikey;
    public DataFetcherEntity(){}
    public DataFetcherEntity(ObjectId id, String apikey) {
        this.id = id;
        this.apikey = apikey;
    }

    public String getApikey() {
        return apikey;
    }

}
